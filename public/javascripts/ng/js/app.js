'use strict';

/* App Module */

angular.module('therightsize', ['therightsizeFilters', 'therightsizeServices']).
  config(['$routeProvider', function($routeProvider) {
  $routeProvider.
      when('/brands', {templateUrl: 'assets/partials/brands-list.html', controller: BrandsListCtrl}).
      when('/brands/:brandId', {templateUrl: 'assets/partials/brand-detail.html', controller: BrandDetailCtrl}).
      otherwise({redirectTo: '/brands'});
}]);
