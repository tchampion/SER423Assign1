/**
 * Copyright 2018 Terin Champion,
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p/>
 * Purpose: Displays information about a PlaceDescription obtained from a JSON file.
 *
 * @author Terin Champion terinchampion@gmail.com
 * @version March, 2018
 */


import Foundation

class PlaceDescription {
    var name: String
    var description: String
    var category: String
    var addressTitle: String
    var addressStreet: String
    var elevation: Double
    var latitude: Double
    var longitude: Double
    
    init(jsonStr: String){
        self.name = ""
        self.description = ""
        self.category = ""
        self.addressTitle = ""
        self.addressStreet = ""
        self.elevation = 0
        self.latitude = 0
        self.longitude = 0
        
        if let data: Data = jsonStr.data(using: String.Encoding.utf8){
            do{
                let dict = try JSONSerialization.jsonObject(with: data,options:.mutableContainers) as?[String:Any]
                self.name = (dict!["name"] as? String)!
                self.description = (dict!["description"] as? String)!
                self.category = (dict!["category"] as? String)!
                self.addressTitle = (dict!["address-title"] as? String)!
                self.addressStreet = (dict!["address-street"] as? String)!
                self.elevation = (dict!["elevation"] as? Double)!
                self.latitude = (dict!["latitude"] as? Double)!
                self.longitude = (dict!["longitude"] as? Double)!            }catch{
                    print("unable to convert to dictionary")
            }
        }
    }
    
    func toJsonString()-> String{
        var jsonStr = "";
        let dict:[String:Any] = ["name":name, "description":description, "category":category, "address-title":addressTitle, "address-street":addressStreet, "elevation":elevation, "latitude":latitude, "longitude":longitude] as [String:Any]
        do{
            let jsonData:Data = try JSONSerialization.data(withJSONObject: dict, options: JSONSerialization.WritingOptions.prettyPrinted)
            jsonStr = NSString(data: jsonData, encoding: String.Encoding.utf8.rawValue)! as String
        }catch let error as NSError{
            print(error)
        }
        return jsonStr
    }
}
