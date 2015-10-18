/**
 * 
 */

theModule.controller('feedCtrl', function($scope, $http, $rootScope){
	
   var userInfoObj = {
       userId: "1234567890",
       dynamicFeedId: 1,
       staticFeedId: 1,
       topicAtLastSync: "",
       topicNow: "",
       gpsLatitudeAtLastSync: 128.123400,
       gpsLongitudeAtLastSync: 128.123400,
       gpsLatitudeNow: 128.123400,
       gpsLongitudeNow: 128.123400,
       sourceDevice: "IOS"
   };
   
   $scope.feed = {
	   textContent: null,
	   userInfo: userInfoObj,
	   expiryDate: null	,
	   linkedFiles : []
   }
	  
   $scope.feedList = {}
	  
   window.navigator.geolocation.getCurrentPosition(function(position) {
         $scope.$apply(function() {
        	$scope.feed.userInfo.gpsLatitudeNow = position.coords.latitude;
            $scope.feed.userInfo.gpsLongitudeNow = position.coords.longitude;

         });
     }, function(error) {
         alert(error);
   });
	  
  $scope.insertFeed = function() {
	  console.log($scope.feed);
	  $http.post('api/insertFeed', $scope.feed).success(function(data, status, headers, config) {
		  console.log(data);
		  alert("Successfully Posted");
		  $scope.feed = {
					 textContent: null,
					 sourceDevice: 'Android',
					 gpsLatitude: $scope.feed.userInfo.gpsLatitudeNow ,
				     gpsLongitude: $scope.feed.userInfo.gpsLongitudeNow,
				     expiryDate: null	  
				  }
		}).error(function(data, status, headers, config) {
			alert("Error in posting. Please try again");
		});
  };
}).directive('mapArea', function() {
	return {
	    restrict: 'C',
	    link: function(scope, element, attrs){
	    	scope.$watch( function(){
	    	var mapOptions = {
	    	          center: new google.maps.LatLng(scope.feed.userInfo.gpsLatitude, scope.feed.userInfo.gpsLongitude),
	    	          zoom: 10,
	    	          mapTypeId: google.maps.MapTypeId.ROADMAP
	    	        }
	    	var map = new google.maps.Map(element[0],mapOptions);
	    	var marker = new google.maps.Marker({
	            map: map,
	            position: new google.maps.LatLng(scope.feed.userInfo.gpsLatitude, scope.feed.userInfo.gpsLongitude),
	            title: 'Posted Location'
	        });
	    	});
	    }
	}
}).directive('inputLocation', function() {
	return {
	    restrict: 'C',
	    link: function(scope, element, attrs){
	    	var mapElement = document.getElementsByClassName("mapArea");
	    	scope.$watch( function(){
	    	element.bind('blur', function() {
	    		var address = element.val();
	    		var geocoder = new google.maps.Geocoder();
	    	    geocoder.geocode( { 'address': address}, function(results, status) {
	    	      if (status == google.maps.GeocoderStatus.OK) {
	    	    	  scope.feed.userInfo.gpsLatitude = results[0].geometry.location.k;
	    	    	  scope.feed.userInfo.gpsLongitude = results[0].geometry.location.D;
	    	    	  var mapOptions = {
	    	    			  center: new google.maps.LatLng(scope.feed.userInfo.gpsLatitude, scope.feed.userInfo.gpsLongitude),
	    	    	          zoom: 10,
	    	    	          mapTypeId: google.maps.MapTypeId.ROADMAP
	    	    	        }
	    	    	var map = new google.maps.Map(mapElement[0],mapOptions);
	    	        map.setCenter(results[0].geometry.location);
	    	        var marker = new google.maps.Marker({
	    	            map: map,
	    	            position: new google.maps.LatLng(scope.feed.userInfo.gpsLatitude, scope.feed.userInfo.gpsLongitude),
	    	        });
	    	      } else {
	    	        console.log("Geocode was not successful for the following reason: " + status);
	    	      }
	        });
	    	});
	    	});
	    }
	}
}).directive('uploadFile', function() {
	return {
	    restrict: 'C',
	    link: function(scope, element, attrs){
	    	
	    	scope.$watch( function(){
		    	$(element).on('change', function(evt) {
		    		var files = evt.target.files;
		    	    var file = files[0];
		    	    var preview = document.getElementById('uploadedImage');
		    	    
		    	    if (files && file) {
		    	        var reader = new FileReader();

		    	        reader.onloadend = function() {
		    	            var binaryString = reader.result;
		    	            preview.src = binaryString;
		    	            console.log(binaryString);
		    	            scope.feed.linkedFiles[0] = { "fileName" : file.name , "fileType": file.type, "base64String" : binaryString}
		    	        };

		    	        reader.readAsDataURL(file);
		    	    }
		    	});
	    	});
		   }
	    }
});
   
