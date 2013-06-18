'use strict';

/* Controllers */

function BrandsListCtrl($scope, Brand) {
  $scope.brands = Brand.query();
}

//PhoneListCtrl.$inject = ['$scope', 'Phone'];



function BrandDetailCtrl($scope, $routeParams, Brand) {
  $scope.brand = Brand.get({brandId: $routeParams.brandId}, function(brand) {
    $scope.mainImageUrl = brand.thumbnail;
  });

  $scope.setImage = function(imageUrl) {
    $scope.mainImageUrl = imageUrl;
  }
}

//PhoneDetailCtrl.$inject = ['$scope', '$routeParams', 'Phone'];
