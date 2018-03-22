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


import UIKit

class ViewController: UIViewController {

    @IBOutlet weak var nameLabel: UILabel!
    @IBOutlet weak var descriptionLabel: UILabel!
    @IBOutlet weak var categoryLabel: UILabel!
    @IBOutlet weak var addressTLabel: UILabel!
    @IBOutlet weak var addressSLabel: UILabel!
    @IBOutlet weak var elevationLabel: UILabel!
    @IBOutlet weak var latitudeLabel: UILabel!
    @IBOutlet weak var longitudeLabel: UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        let file = Bundle.main.path(forResource: "place", ofType: "json")
        var place = ""
        do{
            place = try String(contentsOfFile: file!, encoding: String.Encoding.utf8)
        } catch let error as NSError{
            print("Failed to read from file")
            print(error)
        }
        let pd = PlaceDescription(jsonStr: place)
        nameLabel.text = pd.name
        descriptionLabel.text = pd.description
        categoryLabel.text = pd.category
        addressTLabel.text = pd.addressTitle
        addressSLabel.text = pd.addressStreet
        elevationLabel.text = String(pd.elevation)
        latitudeLabel.text = String(pd.latitude)
        longitudeLabel.text = String(pd.longitude)
        
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


}

