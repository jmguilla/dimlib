'use strict';

/* Controllers */
function ContributeCtrl($scope, Item){
	$scope.items = Item.query();
	$scope.itemSelected = function(){
		if($scope.item != null){
			$scope.sizes = $scope.item.productTypes[0].sizes;
			$scope.sizesDisabled = false;
		}
	};
	$scope.item = null;
	$scope.sizes = null;
	$scope.sizesDisabled = true;
}

function WelcomeCtrl($scope, Messages) {
	$scope.Messages = Messages.query();
}


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
