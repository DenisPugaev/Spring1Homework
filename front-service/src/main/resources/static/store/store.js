angular.module('my-market').controller('storeController', function ($scope, $http, $location, $localStorage) {
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
            $scope.ProductsPage = response.data;
            $scope.paginationArray = $scope.generatePagesIndexes(1, $scope.ProductsPage.totalPages);
        });
    };

    $scope.resetForm = function() {
        $scope.filter.min_price = null;
        $scope.filter.max_price = null;
        $scope.filter.title_part = null;
        $scope.loadProducts();
    };

    $scope.generatePagesIndexes = function (startPage, endPage) {
        let arr = [];
        for (let i = startPage; i < endPage + 1; i++) {
            arr.push(i);
        }
        return arr;
    }


    $scope.addToCart = function (productId) {
        $http.get('http://localhost:5555/cart/api/v1/cart/' + $localStorage.springWebGuestCartId + '/add/' + productId)
            .then(function (response) {
            });
    }


    $scope.loadProducts();

});