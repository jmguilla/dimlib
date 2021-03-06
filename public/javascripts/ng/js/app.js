'use strict';

/* App Module */

angular.module('dimlib', ['dimlibFilters', 'dimlibServices', 'dimlibControllers']).
  config(['$routeProvider', function($routeProvider) {
  $routeProvider.
      when('/welcome', {templateUrl: '/partials/welcome', controller: WelcomeCtrl}).
      when('/brands', {templateUrl: '/partials/brands', controller: BrandsListCtrl}).
      when('/brands/:brandId', {templateUrl: '/partials/brand_details', controller: BrandDetailCtrl}).
      when('/brands/:brandId/items/:itemId', {templateUrl: '/partials/item_details', controller: ItemDetailCtrl}).
      when('/contribute', {templateUrl: '/partials/contribute', controller: ContributeCtrl}).
      when('/dashboard', {templateUrl: '/partials/dashboard', controller: DashboardCtrl}).
      when('/contribs', {templateUrl: '/partials/contribs', controller: ContribsCtrl}).
      when('/new_request', {templateUrl: '/partials/new_request', controller: NewRequestCtrl}).
      when('/new_request', {templateUrl: '/partials/new_request', controller: NewRequestCtrl}).
      when('/account/dashboard', {templateUrl: '/partials/account/dashboard', controller: DashboardCtrl}).
      when('/account/requests', {templateUrl: '/partials/account/requests', controller: RequestsCtrl}).
      when('/account/contribs', {templateUrl: '/partials/account/contribs', controller: ContribsCtrl}).
      when('/account/profile', {templateUrl: '/partials/account/profile', controller: ProfileCtrl}).
      when('/login', {templateUrl: '/login'}).
      when('/logout', {templateUrl: '/logout'}).
      when('/password', {templateUrl: '/partials/password'}).
      when('/signup', {templateUrl: '/signup'}).
      otherwise({redirectTo: '/welcome'});
}])
.config(["$locationProvider", function($locationProvider) {
    return $locationProvider.html5Mode(true).hashPrefix("!");
}
]);
