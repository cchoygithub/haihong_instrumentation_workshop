import { useEffect, useState,useCallback } from 'react'
import {useParams, useNavigate} from 'react-router-dom'
import { retrieveproductApi, updateproductApi, createproductApi } from './api/ProductApiService'
import {Formik, Form, Field, ErrorMessage} from 'formik'

export default function ProductComponent() {
    
    const {id} = useParams()
    console.log("productid"+id)

    const [vendor, setVendor] = useState(generateRandomString(5))
    const [name, setName] = useState(generateRandomString(6))
    const [price, setPrice] = useState(2002)
    const [quantityavailable, setquantityavailable] = useState(1993)


    const initialValues={vendor,name,price,quantityavailable}

    const navigate = useNavigate()    

    const retrieveProducts = useCallback(() => {
        if (id !== "-1") {
            retrieveproductApi(id)
            .then(response => {
                setVendor(response.data.vendor);
                setName(response.data.name);
                setPrice(response.data.price);
                setquantityavailable(response.data.quantityavailable);
            })
            .catch(error => console.log(error));
        }
    }, [id]);

    useEffect(() => {
        retrieveProducts();
    }, [id, retrieveProducts]);
    

  function generateRandomString(length) {
    const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz';
    let result = '';
    for (let i = 0; i < length; i++) {
      result += characters.charAt(Math.floor(Math.random() * characters.length));
    }
    return result;
  }
        
    // function retrieveProducts(){
    //     if(id !== "-1") {
    //         retrieveproductApi(id)
    //         .then(response => {
    //             setVendor(response.data.vendor)
    //             setName(response.data.name)
    //             setPrice(response.data.price)
    //             setquantityavailable(response.data.quantityavailable)                
    //         })
    //         .catch(error => console.log(error))
    //     }
    // }

    function onSubmit(values) {
        console.log(values)
        
        const product = {
            id: id,
            vendor: values.vendor,
            name: values.name,
            price: values.price,
            quantityavailable: values.quantityavailable
        }

        if(id==="-1") {
            createproductApi(product)
            .then(response => {
                navigate('/products')
            })
            .catch(error => console.log(error))
    
        } else {
            updateproductApi(id,product)
            .then(response => {
                navigate('/products')
            })
            .catch(error => console.log(error))
        }
    }

    function validate(values) {
        console.log(values)
        let errors = {
            // description: 'Enter a valid description',
            // targetDate: 'Enter a valid target date'
        }

        if(values.vendor.length<5) {
            errors.vendor = 'vendor atleast 5 characters'
        }

        if(values.name.length<5) {
            errors.name = 'name atleast 5 characters'
        }
        if(values.price<=0) {
            errors.price = 'Enter greater than 0'
        }

        console.log(values)
        return errors
    }

    return (
        <div className="container" style={{ width: "60%" }}  >
            <h1 className='bordered-text'>Enter Product Details </h1>
            <div>
                <Formik initialValues={ initialValues } 
                    enableReinitialize = {true}
                    onSubmit = {onSubmit}
                    validate = {validate}
                    validateOnChange = {false}
                    validateOnBlur = {false}
                >
                {
                    (props) => (
                        <Form>
                            <ErrorMessage 
                                name="vendor"
                                component="div"
                                className = "alert alert-warning"
                            />
                            
                            <ErrorMessage 
                                name="name"
                                component="div"
                                className = "alert alert-warning"
                            />

                            <ErrorMessage 
                                name="price"
                                component="div"
                                className = "alert alert-warning"
                            />
                            
                            <ErrorMessage 
                                name="quantityavailable"
                                component="div"
                                className = "alert alert-warning"
                            />
                            <fieldset className="form-group">
                                <label className="btn btn-light btn-lg" >vendor</label>
                                <Field type="text" className="form-control " name="vendor" ></Field>
                            </fieldset>
                            <fieldset className="form-group">
                                <label className="btn btn-light btn-lg">name</label>
                                <Field type="text" className="form-control" name="name"/>
                            </fieldset>
                            <fieldset className="form-group">
                                <label className="btn btn-light btn-lg">price</label>
                                <Field type="number" className="form-control" name="price"/>                                
                            </fieldset>     

                            <fieldset className="form-group">
                                <label className="btn btn-light btn-lg">quantity available</label>
                                <Field type="number" className="form-control" name="quantityavailable"/>                                
                            </fieldset>                                                    
                            <div>
                                <button className="btn btn-success m-5 wider-button" type="submit">Save</button>
                            </div>
                        </Form>
                    )
                }
                </Formik>
            </div>

        </div>
    )
}