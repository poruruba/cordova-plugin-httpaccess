import Foundation

@objc(HttpAccessPlugin)
class HttpAccessPlugin : CDVPlugin
{
    override
    func pluginInitialize() {
    }
    
    @objc(doPostText:)
    func doPostText(command: CDVInvokedUrlCommand)
    {
        NSLog("doPostText called")
        let arg1 = command.arguments[1] as? String
        let arg2 = command.arguments[2] as? Dictionary<String, String>
        guard let arg0 = command.arguments[0] as? String, let arg3 = command.arguments[3] as? Int else {
            NSLog("Parameter invalid")
            let pluginResult:CDVPluginResult = CDVPluginResult(status:CDVCommandStatus_ERROR, messageAs: "Parameter Invalid")
            self.commandDelegate.send(pluginResult, callbackId: command.callbackId)
            return
        }
        
        Task{
            do{
                let result = try await HttpAccess.doPostText(requestUrl: arg0, inputJson: arg1, headers: arg2, connTimeout: arg3)
                let pluginResult = CDVPluginResult(status: CDVCommandStatus_OK, messageAs: ["result": result!] )
                commandDelegate.send(pluginResult, callbackId: command.callbackId)
            }catch{
                let pluginResult:CDVPluginResult = CDVPluginResult(status:CDVCommandStatus_ERROR, messageAs: "Rutime Error: \(error)")
                self.commandDelegate.send(pluginResult, callbackId: command.callbackId)
                return
            }
        }
    }
    
    @objc(doGetText:)
    func doGetText(command: CDVInvokedUrlCommand)
    {
        NSLog("doGetText called")
        let arg1 = command.arguments[1] as? Dictionary<String, String>
        guard let arg0 = command.arguments[0] as? String, let arg2 = command.arguments[2] as? Int else {
            NSLog("Parameter invalid")
            let pluginResult:CDVPluginResult = CDVPluginResult(status:CDVCommandStatus_ERROR, messageAs: "Parameter Invalid")
            self.commandDelegate.send(pluginResult, callbackId: command.callbackId)
            return
        }
        
        Task{
            do{
                let result = try await HttpAccess.doGetText(requestUrl: arg0, headers: arg1, connTimeout: arg2)
                let pluginResult = CDVPluginResult(status: CDVCommandStatus_OK, messageAs: ["result": result!] )
                commandDelegate.send(pluginResult, callbackId: command.callbackId)
            }catch{
                let pluginResult:CDVPluginResult = CDVPluginResult(status:CDVCommandStatus_ERROR, messageAs: "Rutime Error: \(error)")
                self.commandDelegate.send(pluginResult, callbackId: command.callbackId)
                return
            }
        }
    }
    
    @objc(doPostBinary:)
    func doPostBinary(command: CDVInvokedUrlCommand)
    {
        NSLog("doPostBinary called")
         let arg1 = command.arguments[1] as? String
         let arg2 = command.arguments[2] as? Dictionary<String, String>
        guard let arg0 = command.arguments[0] as? String, let arg3 = command.arguments[3] as? Int else {
            NSLog("Parameter invalid")
            let pluginResult:CDVPluginResult = CDVPluginResult(status:CDVCommandStatus_ERROR, messageAs: "Parameter Invalid")
            self.commandDelegate.send(pluginResult, callbackId: command.callbackId)
            return
        }

        Task{
            do{
                let result = try await HttpAccess.doPostBinary(requestUrl: arg0, inputJson: arg1, headers: arg2, connTimeout: arg3)
                let pluginResult = CDVPluginResult(status: CDVCommandStatus_OK,
                                                   messageAs: ["result": Array(result.binary), "mimeType": result.mimeType] )
                commandDelegate.send(pluginResult, callbackId: command.callbackId)
            }catch{
                let pluginResult:CDVPluginResult = CDVPluginResult(status:CDVCommandStatus_ERROR, messageAs: "Rutime Error: \(error)")
                self.commandDelegate.send(pluginResult, callbackId: command.callbackId)
                return
            }
        }
    }

    @objc(doGetBinary:)
    func doGetBinary(command: CDVInvokedUrlCommand)
    {
        NSLog("doGetBinary called")
        let arg1 = command.arguments[1] as? Dictionary<String, String>
        guard let arg0 = command.arguments[0] as? String, let arg2 = command.arguments[2] as? Int else {
            NSLog("Parameter invalid")
            let pluginResult:CDVPluginResult = CDVPluginResult(status:CDVCommandStatus_ERROR, messageAs: "Parameter Invalid")
            self.commandDelegate.send(pluginResult, callbackId: command.callbackId)
            return
        }

        Task{
            do{
                let result = try await HttpAccess.doGetBinary(requestUrl: arg0, headers: arg1, connTimeout: arg2)
                let pluginResult = CDVPluginResult(status: CDVCommandStatus_OK,
                                                   messageAs: ["result": Array(result.binary), "mimeType": result.mimeType] )
                commandDelegate.send(pluginResult, callbackId: command.callbackId)
            }catch{
                let pluginResult:CDVPluginResult = CDVPluginResult(status:CDVCommandStatus_ERROR, messageAs: "Rutime Error: \(error)")
                self.commandDelegate.send(pluginResult, callbackId: command.callbackId)
                return
            }
        }
    }
}
