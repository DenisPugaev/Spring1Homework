(function () {
    angular
        .module('my-market', ['ngRoute', 'ngStorage'])
        .config(config)
        .run(run);

    function config($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'welcome/welcome.html',
                controller: 'welcomeController'
            })
            .when('/store', {
                templateUrl: 'store/store.html',
                controller: 'storeController'
            })
            .when('/cart', {
                templateUrl: 'cart/cart.html',
                controller: 'cartController'
            })
            .when('/admin', {
                templateUrl: 'admin/admin.html',
                controller: 'adminController'
            })
            .when('/orders', {
                templateUrl: 'orders/orders.html',
                controller: 'ordersController'
            })
            .otherwise({
                redirectTo: '/'
            });
    }

    function run($rootScope, $http, $localStorage) {
        if ($localStorage.springWebUser) {
            try {
                let jwt = $localStorage.springWebUser.token;
                let payload = JSON.parse(atob(jwt.split('.')[1]));
                let currentTime = parseInt(new Date().getTime() / 1000);
                if (currentTime > payload.exp) {
                    console.log("Token is expired!!!");
                    delete $localStorage.springWebUser;
                    $http.defaults.headers.common.Authorization = '';
                }
            } catch (e) {
            }

            if ($localStorage.springWebUser) {
                $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.springWebUser.token;
            }
        }
        if (!$localStorage.springWebGuestCartId) {
            $http.get('http://localhost:5555/cart/api/v1/cart/generate')
                .then(function successCallback(response) {
                    $localStorage.springWebGuestCartId = response.data.value;
                    console.log( "Гостевой ID:"+ $localStorage.springWebGuestCartId)

                }, function errorCallback(response) {
                alert('Ошибка генерации ID корзины!');
                console.log($localStorage.springWebGuestCartId);
            });
        }
    }
})();

angular.module('my-market').controller('indexController', function ($rootScope, $scope, $http, $location, $localStorage) {



    $scope.tryToAuth = function () {
        $http.post('http://localhost:5555/auth/token', $scope.user)
            .then(function successCallback(response) {
                console.log('ОТВЕТ:',response);
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.springWebUser = {username: $scope.user.username, token: response.data.token};
                    console.log($scope.user.username, response.data.token);
                    $scope.user.username = null;
                    $scope.user.password = null;
                    $scope.userShow = $localStorage.springWebUser.username;

                    $http.get('http://localhost:5555/cart/api/v1/cart/' + $localStorage.springWebGuestCartId + '/merge')
                        .then(function successCallback(response) {
                        });
                    $location.path('/');
                }
            }, function errorCallback(response) {
                alert('Ошибка входа!');
                console.log($scope.user.username, response.data.token);
            });
    };

    $scope.tryToLogout = function () {
        $scope.clearUser();
        $scope.user = null;
        $scope.userShow =null;
        $location.path('/');
    };

    $scope.clearUser = function () {
        delete $localStorage.springWebUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $rootScope.isUserLoggedIn = function () {
        return !!$localStorage.springWebUser;
    };

});