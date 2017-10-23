angular
		.module("MyApp")
		.constant("loginUrl", "http://localhost/MointorApp/user/login")
		.constant("selUrl", "http://localhost/Web/rest/hello/getPositions")
		.constant("allUrl", "http://localhost/Gps/rest/position/allPositions")
		.controller(
				"loginCtrl",
				function($scope, $http, $location, loginUrl) {
					$scope.log = function(uname, pass) {
						if (uname === "google" && pass === "google") {
							$location.path("/map");
						} else {
							$scope.loginError = "Wrong username or password ,make sure that the data is good!";
						}
					};
				})
		.controller(
				"mapCtrl",
				function($scope, $http, selUrl, allUrl) {

					$scope.message = "Enter the userId,start and end date in order to monitor the user";
					function initMap() {

						var mapOptions = {
							center : lastPos,
							zoom : 4,
							mapTypeId : google.maps.MapTypeId.ROADMAP
						};
						var mapElement = document.getElementById('map');
						var lineOptions = {
							path : line,
							strokeWeight : 7,
							strokeColor : '#FF0000',
							strokeOpacity : 0.8
						};

						var poly = new google.maps.Polyline(lineOptions);
						map = new google.maps.Map(mapElement, mapOptions);
						poly.setMap(map);

					}
					function addLine(data) {
						line = [];
						for (var i = 0; i < data.length; i++) {
							var tempLatLng = new google.maps.LatLng(
									Number(data[i].latitude),
									Number(data[i].longitude));
							line.push(tempLatLng);
							lastPos = tempLatLng;
						}

						initMap();

					}
					$scope.getCoordinates = function(userId, startDate, endDate) {
						$scope.status = "show";
						var promise = $http
								.get("http://localhost/Gps/rest/position/getPositions?userId="
										+ userId
										+ "&start="
										+ startDate
										+ "&end=" + endDate);
						promise.success(function(data) {
							$scope.positions = data;
							addLine(data);

						}).error(function(error) {
							$scope.positions.error = error;
						});
					};
					$scope.allCoordinates = function(userId) {
						$scope.status = "show";
						var promise = $http
								.get("http://localhost/Gps/rest/position/allPositions?userId="
										+ userId);
						promise.success(function(data) {
							$scope.positions = data;
							console.log("data :" + data);
							addLine(data);

						}).error(function(error) {
							$scope.positions.error = error;
						});
					};

				})
		.filter(
				"filter",
				function() {
					return function(data, startDate, endDate, showAll) {
						if (angular.isArray(data)
								&& angular.isString(startDate)
								&& angular.isString(endDate)) {
							var result = [];
							angular
									.forEach(
											data,
											function(item) {
												if ((item.date >= startDate && item.date <= endDate)
														|| showAll == true) {
													result.push(item);
												}
											});
							return result;
						} else {
							return data;
						}
					};
				});