/**
 * 
 */

theModule.controller('feedListCtrl', function($scope, $http, $rootScope){
   $scope.params = {
	     	   
   };
   $scope.params.userInfo = {
		   userId: "1234567890",
	       dynamicFeedId: 1,
	       staticFeedId: 1,
	       topicAtLastSync: "All topics",
	       topicNow: "All topics",
	       gpsLatitudeAtLastSync: 0.0,
	       gpsLongitudeAtLastSync: 0.0,
	       gpsLatitudeNow: 0.0,
	       gpsLongitudeNow: 0.0,
	       sourceDevice: "IOS"
   };
   
   $scope.feedList = {}
   
	   window.navigator.geolocation.getCurrentPosition(function(position) {
	       $scope.$apply(function() {
	    	 $scope.params.userInfo.gpsLatitudeAtLastSync = position.coords.latitude;
		     $scope.params.userInfo.gpsLongitudeAtLastSync = position.coords.longitude;
	      	 $scope.params.userInfo.gpsLatitudeNow = position.coords.latitude;
	      	 $scope.params.userInfo.gpsLongitudeNow = position.coords.longitude;
		   	  $http.post('api/getDynamicFeeds', $scope.params).success(function(data, status, headers, config) {
				  console.log(data);
		   		  $scope.feedList = data.feeds
			  }).error(function(data, status, headers, config) {
			  });
	       });
	   }, function(error) {
	       alert(error);
	   });	
  
   $scope.refresh = function() {
	   $http.post('api/getDynamicFeeds', $scope.params).success(function(data, status, headers, config) {
			  $scope.feedList = data
		  }).error(function(data, status, headers, config) {
		  });
   }


}).directive('mapArea', function() {
	return {
	    restrict: 'C',
	    link: function(scope, element, attrs){
	    	var mapOptions = {
	    	          center: new google.maps.LatLng(scope.feed.gpsLatitude, scope.feed.gpsLongitude),
	    	          zoom: 10,
	    	          mapTypeId: google.maps.MapTypeId.ROADMAP
	    	        }
	    	var map = new google.maps.Map(element[0],mapOptions);
	    	var marker = new google.maps.Marker({
	            map: map,
	            position: new google.maps.LatLng(scope.feed.gpsLatitude, scope.feed.gpsLongitude),
	            title: 'Posted Location'
	        });
	    }
	}
}).directive('inputLocation', function() {
	return {
	    restrict: 'C',
	    link: function(scope, element, attrs){
	    	var mapElement = document.getElementsByClassName("inputMapArea");
	    	scope.$watch( function(){
	    	element.bind('blur', function() {
	    		var address = element.val();
	    		var geocoder = new google.maps.Geocoder();
	    	    geocoder.geocode( { 'address': address}, function(results, status) {
	    	      if (status == google.maps.GeocoderStatus.OK) {
	    	    	  scope.params.gpsLatitudeNow = results[0].geometry.location.k;
	    	    	  scope.params.gpsLongitudeNow = results[0].geometry.location.D;
	    	    	  var mapOptions = {
	    	    			  center: new google.maps.LatLng(scope.params.gpsLatitudeNow, scope.params.gpsLongitudeNow),
	    	    	          zoom: 10,
	    	    	          mapTypeId: google.maps.MapTypeId.ROADMAP
	    	    	        }
	    	    	var map = new google.maps.Map(mapElement[0],mapOptions);
	    	        map.setCenter(results[0].geometry.location);
	    	        var marker = new google.maps.Marker({
	    	            map: map,
	    	            position: new google.maps.LatLng(scope.params.gpsLatitudeNow, scope.params.gpsLongitudeNow),
	    	        });
	    	      } else {
	    	        console.log("Geocode was not successful for the following reason: " + status);
	    	      }
	        });
	    	});
	    	});
	    }
	}
}).directive('inputMapArea', function() {
	return {
	    restrict: 'C',
	    link: function(scope, element, attrs){
	    	scope.$watch( function(){
	    	var mapOptions = {
	    	          center: new google.maps.LatLng(scope.params.gpsLatitudeNow, scope.params.gpsLongitudeNow),
	    	          zoom: 10,
	    	          mapTypeId: google.maps.MapTypeId.ROADMAP
	    	        }
	    	var map = new google.maps.Map(element[0],mapOptions);
	    	var marker = new google.maps.Marker({
	            map: map,
	            position: new google.maps.LatLng(scope.params.gpsLatitudeNow, scope.params.gpsLongitudeNow),
	            title: 'Posted Location'
	        });
	    	});
	    }
	}
});
