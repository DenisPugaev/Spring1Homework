angular.module('my-market').controller('cartController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:5555/cart/';
    $scope.cartID = $localStorage.springWebGuestCartId;

    $scope.loadCart = function () {
        $http({
            url: contextPath + 'api/v1/cart/'+ $localStorage.springWebGuestCartId,
            method: 'GET'
        }).then(function (response) {
            $scope.cart = response.data;
        });
    };

    $scope.disabledCheckOut = function () {
        alert("Для оформления заказа необходимо войти в учетную запись");
    }

    $scope.clearCart = function () {
        $http.get(contextPath + 'api/v1/cart/'+ $localStorage.springWebGuestCartId + '/clear')
            .then(function (response) {
                $scope.loadCart();
            });
    }

    $scope.checkOut = function () {
        console.log("Click checkOut")
        $http({
            url: 'http://localhost:5555/core/api/v1/orders',
            method: 'POST',
            data: $scope.orderDetails
        }).then(function (response) {
            $scope.loadCart();
            $scope.orderDetails = null
        });
    };
    // $scope.deleteProductFromCart= function (productId) {
    //     console.log('Click deleteProductFromCart', productId);
    //     $http.get(contextPath + '/cart/delete/' + productId)
    //         .then(function (response) {
    //             $scope.loadCart();
    //         });
    // };

    $scope.loadCart();
});