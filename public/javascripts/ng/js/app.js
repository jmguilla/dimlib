'use strict';

/* App Module */

angular.module('dimlib', ['dimlibFilters', 'dimlibServices', 'dimlibControllers']).
  config(['$routeProvider', function($routeProvider) {
  $routeProvider.
      when('/welcome', {templateUrl: 'partials/welcome', controller: WelcomeCtrl}).
      when('/brands', {templateUrl: 'assets/partials/brands-list.html', controller: BrandsListCtrl}).
      when('/brands/:brandId', {templateUrl: 'assets/partials/brand-detail.html', controller: BrandDetailCtrl}).
      when('/contribute', {templateUrl: 'partials/contribute', controller: ContributeCtrl}).
      when('/dashboard', {templateUrl: 'partials/dashboard', controller: AccountCtrl}).
      when('/contribs', {templateUrl: 'partials/contribs', controller: AccountCtrl}).
      when('/requests', {templateUrl: 'partials/requests', controller: AccountCtrl}).
      when('/account', {templateUrl: 'partials/account', controller: AccountCtrl}).
      otherwise({redirectTo: '/assets/partials/empty'});
}]);
