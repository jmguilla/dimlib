'use strict';

/* Filters */

angular.module('therightsizeFilters', []).filter('checkmark', function() {
  return function(input) {
    return input ? '\u2713' : '\u2718';
  };
});
