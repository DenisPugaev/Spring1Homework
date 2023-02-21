angular.module('my-market').controller('adminController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:5555/core/';

    $scope.loadProducts = function (pageIndex = 1) {
        $http({
            url: contextPath + 'api/v1/products',
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
    };

    $scope.resetForm = function() {
        $scope.filter.min_price = null;
        $scope.filter.max_price = null;
        $scope.filter.title_part = null;
        $scope.loadProducts();
    };

    $scope.deleteProduct = function (productId) {
        console.log('Click deleteProduct', productId);
        $http.delete(contextPath + 'api/v1/admin/product/' + productId)
            .then(function successCallback(response) {
                alert('Продукт удален ID: ' + productId);
                $scope.loadProducts();
            }, function errorCallback(response) {
                alert('Нет прав на удаление товара!');
            });

    }




    $scope.loadProducts();
});