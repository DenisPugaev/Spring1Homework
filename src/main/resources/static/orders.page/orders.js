angular.module('my-market').controller('ordersController', function ($scope, $http) {
    const contextPath = 'http://localhost:8080/app/';

    $scope.loadOrders = function () {
        $http.get(contextPath + 'api/v1/orders')
            .then(function (response) {
                $scope.MyOrders = response.data;
            });
    }

    $scope.loadOrders();
});