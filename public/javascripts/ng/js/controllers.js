'use strict';
angular.module('dimlibControllers', ['ui.bootstrap']);

/* Controllers */
var ContributeCtrl = function($scope, Item){
	$scope.items = Item.query();
	$scope.itemSelected = function(){
		if($scope.item != null){
			$scope.sizes = $scope.item.productTypes[0].sizes;
			$scope.size = null;
			$scope.satisfaction = null;
			$scope.sizesDisabled = false;
			$scope.satisfactionDisabled = true;
			$scope.voteDisabled = true;
		}else{
			$scope.sizesDisabled = true;
			$scope.satisfactionDisabled = true;
			$scope.voteDisabled = true;
		}
	};
	$scope.sizeSelected = function(){
		if($scope.size != null){
			$scope.satisfaction = null;
			$scope.satisfactionDisabled = false;
		}else{
			$scope.satisfactionDisabled = true;
			$scope.voteDisabled = true;
		}
	}
	$scope.satisfactionSelected = function(){
		if($scope.satisfaction != null){
			$scope.voteDisabled = false;
		}else{
			$scope.voteDisabled = true;
		}
	}
	$scope.item = null;
	$scope.sizes = null;
	$scope.sizesDisabled = true;
	$scope.satisfactionDisabled = true;
	$scope.voteDisabled = true;
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
