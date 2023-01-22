angular.module('app', ['ngStorage']).controller('indexController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:8080/app/api/v1';

    if ($localStorage.springWebUser) {
        $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.springWebUser.token;
    }

    $scope.loadProducts = function (pageIndex=1) {
        $http({
            url: contextPath + '/products',
            method: 'GET',
            params: {
                page: pageIndex,
                min_price: $scope.filter ? $scope.filter.min_price : null,
                max_price: $scope.filter ? $scope.filter.max_price : null,
                title_part: $scope.filter ? $scope.filter.title_part : null
            }
        }).then(function (response) {

            $scope.ProductList = response.data.content;
        });
    }


    $scope.resetForm = function() {
            $scope.filter.min_price = null;
            $scope.filter.max_price = null;
            $scope.filter.title_part = null;
          $scope.loadProducts();
    };

    $scope.deleteProduct = function (productId) {
        console.log('Click deleteProduct', productId);
        $http.delete(contextPath + '/admin/product/' + productId)
            .then(function successCallback(response) {
                alert('Продукт удален ID: ' + productId);
                $scope.loadProducts();
            }, function errorCallback(response) {
                alert('Нет прав на удаление товара!');
            });

    }


    $scope.addProduct = function () {
        console.log('Click addProduct!', $scope.newProduct);
        $http.post(contextPath + '/admin/product', $scope.newProduct)
            .then(function successCallback(response) {
                alert('Продукт добавлен: ' + $scope.newProduct.title);
                $scope.newProduct.title = null;
                $scope.newProduct.price = null;
                $scope.newProduct.manufacturer = null;
                $scope.loadProducts();
            }, function errorCallback(response) {
                alert('Нет прав на добавление товара!');
            });
    };





    $scope.addToCart = function (productId) {
        $http.get(contextPath+'/carts/add/' + productId)
            .then(function (response) {
                $scope.loadCart();
            });
    }

    $scope.clearCart = function () {
        console.log('Click clearCart');
        $http.get(contextPath+'/carts/clear')
            .then(function (response) {
                $scope.loadCart();
            });
    }

    $scope.loadCart = function () {
        $http.get(contextPath+'/carts')
            .then(function (response) {
                $scope.Cart = response.data;
            });
    }

    $scope.deleteProductFromCart= function (productId) {
        console.log('Click deleteProductFromCart', productId);
        $http.get(contextPath + '/carts/delete/' + productId)
            .then(function (response) {
                $scope.loadCart();
            });
    };


    $scope.tryToAuth = function () {
        $http.post('http://localhost:8080/app/auth/token', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.springWebUser = {username: $scope.user.username, token: response.data.token};

                    $scope.user.username = null;
                    $scope.user.password = null;
                }
            }, function errorCallback(response) {
            });
    };

    $scope.tryToLogout = function () {
        $scope.clearUser();
        if ($scope.user.username) {
            $scope.user.username = null;
        }
        if ($scope.user.password) {
            $scope.user.password = null;
        }
    };

    $scope.clearUser = function () {
        delete $localStorage.springWebUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $rootScope.isUserLoggedIn = function () {
        if ($localStorage.springWebUser) {
            return true;
        } else {
            return false;
        }
    };

    $scope.showCurrentUserInfo = function () {
        $http.get('http://localhost:8080/app/api/v1/profile')
            .then(function successCallback(response) {
                alert('MY NAME IS: ' + response.data.username);
            }, function errorCallback(response) {
                alert('Вы не авторизированы!');
            });
    };



    $scope.loadProducts();

});
