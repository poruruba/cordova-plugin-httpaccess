class HttpAccessPlugin{
	doPostText(requestUrl, input, headers, connTimeout){
		return new Promise(function(resolve, reject){
			cordova.exec(
				function(result){
					resolve(result);
				},
				function(err){
					reject(err);
				},
				"HttpAccessPlugin", "doPostText",
				[requestUrl, input, headers, connTimeout]);
		});
	}

	doGetText(requestUrl, headers, connTimeout){
		return new Promise(function(resolve, reject){
			cordova.exec(
				function(result){
					resolve(result);
				},
				function(err){
					reject(err);
				},
				"HttpAccessPlugin", "doGetText",
				[requestUrl, headers, connTimeout]);
		});
	}

	doPostBinary(requestUrl, input, headers, connTimeout){
		return new Promise(function(resolve, reject){
			cordova.exec(
				function(result){
					resolve(result);
				},
				function(err){
					reject(err);
				},
				"HttpAccessPlugin", "doPostBinary",
				[requestUrl, input, headers, connTimeout]);
		});
	}

	doGetBinary(requestUrl, headers, connTimeout){
		return new Promise(function(resolve, reject){
			cordova.exec(
				function(result){
					resolve(result);
				},
				function(err){
					reject(err);
				},
				"HttpAccessPlugin", "doGetBinary",
				[requestUrl, headers, connTimeout]);
		});
	}
}

module.exports = new HttpAccessPlugin();
