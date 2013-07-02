'use strict';

/* Services */

angular.module('dimlibServices', ['ngResource']).
factory('Brand', function($resource){
	  return $resource('rest/brands/:brandId', {}, {
	    query: {method:'GET', params:{brandId:''}, isArray:true}
	  });
}).
factory('Messages', function($resource){
	return $resource('jsmessages', {}, {
		query: {method: 'GET'}
	});
}).
factory('Item', function($resource){
	return $resource('rest/items', {}, {
		query: {method: 'GET',isArray:true}
	});
}).
factory('Contribution', function($resource){
	return $resource('rest/contribution', {}, {
		create: {method: 'POST'},
		query: {method: 'GET', isArray: true}
	})
});
