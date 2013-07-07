'use strict';
angular.module('dimlibControllers', ['ui.bootstrap']);

/* Controllers */
var MainCtrl = function($scope){
	$scope.closeAlert = function(index) {
	    $scope.alerts.splice(index, 1);
	};
	$scope.alerts = [];
}

var NewRequestCtrl = function($scope, Item, Request, Brand){
	$scope.items = Item.query();
	$scope.brands = Brand.query();
	$scope.submitNewItem = function(){
		var item = new Item({brand: $scope.newItemBrand, name: $scope.newItem});
		item.$save({}, function(res){
			$scope.alerts.push(res);
			$scope.newItem = undefined;
			$scope.newItemBrand = undefined;
			$scope.item = res.result;
			$scope.submitNewRequest();
		}, function(res){
			$scope.alerts.push(res.data);
		});
	}
	$scope.submitNewRequest = function(){
		if($scope.newItemBrand != undefined && $scope.newItemBrand != ''){
			//creating a new Item
			if($scope.newItem != undefined && $scope.newItem != ''){
				$scope.submitNewItem();
			}else{
				$scope.alerts.push({type: 'error', msg: 'Aie Aie Aie... Vous avez oublie de remplir le champ designation...'});
			}
		}else{
			//requesting help for an already existing item
			if(!$scope.item.id){
				$scope.alerts.push({type: 'error', msg: 'Aie Aie Aie... Vous avez du faire un faute de frappe... Le produit que vous demandez n\'existe pas... Vous voulez le créer?'});
				return;
			}
			var request = new Request({itemId: $scope.item.id});
			request.$save({}, function(res){
				$scope.alerts.push(res);
			}, function(res){
				$scope.alerts.push(res.data);
			});
		}
	};
	$scope.item = undefined;
	$scope.newItemBrand = undefined;
	$scope.newItem = undefined;
	$scope.tmpItem = { brand: {name: 'brand'}, name: 'item'};
}

var RequestsCtrl = function($scope, Request){
	$scope.requests = Request.query();
}

var DashboardCtrl = function($scope){
	
}

var ContribsCtrl = function($scope, Contribution){
	$scope.contribs = Contribution.query();
}

var ProfileCtrl = function($scope){
	
}

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
			$scope.alerts.push(res.data);
			$scope.resetFields();
			$scope.item = $scope.items[0];
		});
	};
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
