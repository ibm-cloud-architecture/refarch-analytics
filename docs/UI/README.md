# User Interface for customer management
This note explains the code for the Angular 4 component added to the WebApp CaseInc, this is more to illustrate the very simple way to plug and play new feature in this app.

## Add customer UI
1. Under the client/app add a folder customer
1. Add one component to list the customers in a table with the action, edit, delete and new customer. The file is customers.component.ts

  ```javascript
  @Component({
      selector: 'customers',
      styleUrls:['customer.css'],
      templateUrl:'customers.component.html'
    })

  export class CustomersComponent implements OnInit {
    customers : Customer[]=[];
    message: string = "May take some time to load....";
    loading: boolean= true;
    index: number = -1;

    constructor(private router: Router, private custService : CustomersService){
    }
  ```

1. Add a Customer class to define the data model for the user interface. It does not need to be the same as the back end data model.
  ```javascript
  export class Customer {
    id: number;
    name : string;
    age: number;
    usage:number;
    ratePlan: number;
  }

  ```
1. Add a service to call the server side API.
  ```javascript
  @Injectable()
  export class CustomersService {
    private custUrl ='/api/cust';

    constructor(private http: Http) {

    };

    getCustomers(): Observable<any>{
  return this.http.get(this.custUrl+'/customers')
       .map((res:Response) =>
        res.json())
      }

  ```

1. Add the backend service which is a proxy to the other backend. In the api definition file add the new URL path:
  ```javascript
  const customer    = require('./features/customerProxy');
  //....
  app.get('/api/cust/customers', (req,res) => {
    customer.getCustomers(config,req,res);
  })
  app.get('/api/cust/customers/:id', (req,res) => {
    customer.getCustomer(config,req,res);
  })
  ```

  1. Add the customer proxy feature to call the backend system. This module is using HTTP and Request libraries to make the call to the RESTful API. See the code customerProxy.js. 
    ```
    getCustomers : function(config,req,res){
      var opts = buildOptions('GET','/customers',config);
      request(opts,
          function (error, response, body) {
            if (error) {
              console.error("Process Request Error: "+error);
              return res.status(500).send({error:error});
            }
            res.send(body);
          }
         );
    }
    ```
