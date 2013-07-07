'use strict';

/* Services */

angular.module('dimlibServices', ['ngResource']).
factory('Brand', function($resource){
	  return $resource('/rest/brands/:brandId', {}, {
	    query: {method:'GET', params:{brandId:''}, isArray:true}
	  });
}).
factory('Messages', function($resource){
	return $resource('/rest/jsmessages', {}, {
		query: {method: 'GET'}
	});
}).
factory('Item', function($resource){
	return $resource('/rest/items', {}, {
		query: {method: 'GET',isArray:true}
	});
}).
factory('Contribution', function($resource){
	return $resource('/rest/contributions', {}, {
		create: {method: 'POST'},
		query: {method: 'GET', isArray: true}
	})
}).
factory('Request', function($resource){
	return $resource('/rest/requests', {}, {
		query: {method: 'GET', isArray: true}
	})
}).
factory('User', function($resource){
	return $resource('/rest/users', {}, {
		query: {method: 'GET'}
	})
})
;
