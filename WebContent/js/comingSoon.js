
theModule.controller('ComingSoonCtrl', function($scope, $http, $rootScope){

  $scope.toggled = function(open) {
    console.log('Dropdown is now: ', open);
  };

  $scope.toggleDropdown = function($event) {
    $event.preventDefault();
    $event.stopPropagation();
    $scope.status.isopen = !$scope.status.isopen;
  };
	$scope.cities = $rootScope.cities;
	$scope.city = $scope.cities[0];		

	$scope.changeCity = function(c){
		$scope.city = c;
	};
});
