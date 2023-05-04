import Foundation

enum HttpAccessError: Error{
    case StatusCodeError
}

class HttpAccess{
    static func doPostText(requestUrl: String,
                           inputJson : String?, headers: Dictionary<String, String>?, connTimeout: Int) async throws -> String? {
        let url = URL(string: requestUrl)!
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        request.timeoutInterval = Double(connTimeout)
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        if let _inputJson = inputJson {
            request.httpBody = _inputJson.data(using: .utf8)
        }
        if let _headers = headers {
            for (key, value) in _headers {
                request.setValue(value, forHTTPHeaderField: key)
            }
        }
        
        let (data, response) = try await URLSession.shared.data(for: request)
        let urlResponse = response as! HTTPURLResponse
        if urlResponse.statusCode != 200 {
            throw HttpAccessError.StatusCodeError
        }
        
        return String(data: data, encoding: .utf8)
    }
    
    static func doGetText(requestUrl: String,
                          headers: Dictionary<String, String>?, connTimeout: Int) async throws -> String? {
        let url = URL(string: requestUrl)!
        
        var request = URLRequest(url: url)
        request.httpMethod = "GET"
        request.timeoutInterval = Double(connTimeout)
        
        if let _headers = headers {
            for (key, value) in _headers {
                request.setValue(value, forHTTPHeaderField: key)
            }
        }
        
        let (data, response) = try await URLSession.shared.data(for: request)
        let urlResponse = response as! HTTPURLResponse
        if urlResponse.statusCode != 200 {
            throw HttpAccessError.StatusCodeError
        }
        return String(data: data, encoding: .utf8)
    }
    
    static func doPostBinary(requestUrl: String,
                             inputJson : String?, headers: Dictionary<String, String>?, connTimeout: Int) async throws -> (mimeType: String, binary: [UInt8]) {
        let url = URL(string: requestUrl)!
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        request.timeoutInterval = Double(connTimeout)
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        if let _inputJson = inputJson {
            request.httpBody = _inputJson.data(using: .utf8)
        }
        if let _headers = headers {
            for (key, value) in _headers {
                request.setValue(value, forHTTPHeaderField: key)
            }
        }
        
        let (locationUrl, response) = try await URLSession.shared.download(for: request)
        let urlResponse = response as! HTTPURLResponse
        if urlResponse.statusCode != 200 {
            throw HttpAccessError.StatusCodeError
        }
        
        let data = try Data(contentsOf: locationUrl)
        let responseValues = data.withUnsafeBytes({ (pointer: UnsafeRawBufferPointer) -> [UInt8] in
            let unsafeBufferPointer = pointer.bindMemory(to: UInt8.self)
            let unsafePointer = unsafeBufferPointer.baseAddress!
            return [UInt8](UnsafeBufferPointer(start: unsafePointer, count: data.count))
        })
        
        return (urlResponse.mimeType ?? "application/octet-stream", responseValues)
    }
    
    static func doGetBinary(requestUrl: String,
                            headers: Dictionary<String, String>?, connTimeout: Int) async throws -> (mimeType: String, binary: [UInt8]) {
        let url = URL(string: requestUrl)!
        
        var request = URLRequest(url: url)
        request.httpMethod = "GET"
        request.timeoutInterval = Double(connTimeout)
        
        if let _headers = headers {
            for (key, value) in _headers {
                request.setValue(value, forHTTPHeaderField: key)
            }
        }
        
        let (locationUrl, response) = try await URLSession.shared.download(for: request)
        let urlResponse = response as! HTTPURLResponse
        if urlResponse.statusCode != 200 {
            throw HttpAccessError.StatusCodeError
        }
        
        let data = try Data(contentsOf: locationUrl)
        let responseValues = data.withUnsafeBytes({ (pointer: UnsafeRawBufferPointer) -> [UInt8] in
            let unsafeBufferPointer = pointer.bindMemory(to: UInt8.self)
            let unsafePointer = unsafeBufferPointer.baseAddress!
            return [UInt8](UnsafeBufferPointer(start: unsafePointer, count: data.count))
        })
        
        return (urlResponse.mimeType ?? "application/octet-stream", responseValues)
    }
}
