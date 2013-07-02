'use strict';
angular.module('dimlibControllers', ['ui.bootstrap']);

/* Controllers */
var ContributeCtrl = function($scope, Item, Contribution){
	$scope.items = Item.query();
	$scope.resetFields = function(){
		$scope.sizesDisabled = true;
		$scope.adjustmentDisabled = true;
		$scope.voteDisabled = true;
	};
	$scope.itemSelected = function(){
		if($scope.item != null){
			$scope.sizes = $scope.item.productTypes[0].sizes;
			$scope.size = null;
			$scope.adjustment = null;
			$scope.sizesDisabled = false;
			$scope.adjustmentDisabled = true;
			$scope.voteDisabled = true;
		}else{
			$scope.resetFields();
		}
	};
	$scope.sizeSelected = function(){
		if($scope.size != null){
			$scope.adjustment = null;
			$scope.adjustmentDisabled = false;
		}else{
			$scope.adjustmentDisabled = true;
			$scope.voteDisabled = true;
		}
	};
	$scope.adjustmentSelected = function(){
		if($scope.adjustment != null){
			$scope.voteDisabled = false;
		}else{
			$scope.voteDisabled = true;
		}
	};
	$scope.contribute = function(){
		Contribution.create({itemId: $scope.item.id, sizeId: $scope.size.id, adjustment: $scope.adjustment},
		function(res){
			$scope.alerts.push(res);
			$scope.resetFields();
			$scope.item = $scope.items[0];
		},
		function(res){
			$scope.alerts.push(res);
			$scope.resetFields();
			$scope.item = $scope.items[0];
		});
	};
	$scope.closeAlert = function(index) {
	    $scope.alerts.splice(index, 1);
	};
	$scope.alerts = [];
	$scope.item = null;
	$scope.sizes = null;
	$scope.resetFields();
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
