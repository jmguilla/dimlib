'use strict';

/* Services */

angular.module('therightsizeServices', ['ngResource']).
    factory('Brand', function($resource){
	  return $resource('rest/brands/:brandId', {}, {
	    query: {method:'GET', params:{brandId:''}, isArray:true}
	  });
});
