/**
 * 
 */
theModule.controller('productCtrl',function($scope, $modal, $log, $rootScope, $location, $http){
	$http.get('api/getProducts', {		
    }).success(function(data, status, headers, config) {
    	$scope.productList = data;
    }).error(function(data, status, headers, config) {
    });
	$scope.productList = {};
});