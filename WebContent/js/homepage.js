
theModule.controller('homePageCtrl', function($scope, $modal, $log, $rootScope){

  $scope.toggled = function(open) {
    //console.log('Dropdown is now: ', open);
  };

  $scope.toggleDropdown = function($event) {
    $event.preventDefault();
    $event.stopPropagation();
    $scope.status.isopen = !$scope.status.isopen;
  };

	$scope.cities = $rootScope.cities;
	$scope.searchDistances = $rootScope.searchDistances;
	$scope.searchDistance = $scope.searchDistances[0];
	$scope.city = $scope.cities[0];	

	$scope.changeCity = function(c){
		$scope.city = c;
	};

	$scope.changeDist = function(d){
		$scope.searchDistance  = d;
	};

	$scope.search = function(){

	   if(navigator.geolocation){
	      var options = {timeout:3000};
	      navigator.geolocation.getCurrentPosition(function(position){

  				 		$scope.position = position;
  				 		$scope.changeUrl();
	      			},
	      			 function(err){
							  if(err.code == 1) {
							    $scope.errorMessage ="Error: Access is denied!";
							  }else if( err.code == 2) {
							    $scope.errorMessage ="Error: Position is unavailable!";
							  }
							  $scope.handleLocationFail();
	  					},  options);

	   }else{
	      $scope.errorMessage = "Sorry, browser does not support geolocation!";
	      $scope.handleLocationFail();
	   }
	};

	$scope.zipcode = '120605';

  $scope.handleLocationFail = function (size) {

    var modalInstance = $modal.open({
      templateUrl: 'partials/homepageModel.html',
      controller: 'ModalInstanceCtrl',
      scope: $scope,
      resolve: {
      }
    });

    modalInstance.result.then(function (zipcode) {
      $scope.zipcode = zipcode;
      $scope.changeUrl();
    }, function () { // when cancel is clicked.
    });
  };
  
});


theModule.controller('ModalInstanceCtrl', function ($scope, $modalInstance) {


  $scope.ok = function () {
    $modalInstance.close($scope.zipcode);
  };

  $scope.cancel = function () {
    $modalInstance.dismiss('cancel');
  };
});


