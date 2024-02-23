import { useEffect, useState } from "react"
import {useNavigate} from 'react-router-dom'
import { retrieveAllproductsApi, deleteproductApi,orderproductApi,vendordetailsApi } from "./api/ProductApiService"


function ListProductsComponent() { 

    const navigate = useNavigate()

    const [products,setProducts] = useState([])
    const [message,setMessage] = useState("hello again...")
    const [coupons, setCoupons] = useState({}); 
    const [ratings, setRatings] = useState({}); 

    useEffect ( () => refreshProducts(), [])

    function refreshProducts() {
        
        retrieveAllproductsApi()
        .then(response => {
            setProducts(response.data)
        })
        .catch(error => {console.log(error);
          setMessage(error.message)
        })
    
    }

    function deleteproduct(id) {
        console.log('clicked ' + id)
        deleteproductApi(id)
        .then( () => {setMessage(`Delete of product with id = ${id} successful`)
                refreshProducts()})
        .catch((error) => {console.log(error)
            setMessage(error.message +': '+ error.response.data)})
    }

    function updateproduct(id) {
        console.log('clicked ' + id)
        navigate(`/products/${id}`)
    }

    function addnewproduct() {
        navigate(`/products/-1`)
    }
    function orderproduct(product, quantity) {
        console.log('clicked ' + JSON.stringify(product))
        setMessage("ordering... "+product.vendor+ " "+product.name)
        
        product.product=product.name;
        product.quantity=quantity;

        orderproductApi(product)
        .then((response) => {
                setMessage(response.data)
                // refreshProducts()
            }).catch(error => {console.log(error);
            setMessage("==== "+error.message )})
    }
    function getCouponRating(product) {
        setMessage('getting coupon and rating for: ' + product.vendor);
        vendordetailsApi(product)
            .then((response) => {

                setMessage(JSON.stringify(response.data));
                setCoupons(prevCoupons => ({
                    ...prevCoupons,
                    [product.vendor]: response.data.Coupon,
                }));

                setRatings(prevRating => ({
                    ...prevRating,
                    [product.vendor]: response.data.Rating,
                })); 

                console.log(coupons)
            })
            .catch((error) => {
                console.log(error);
                setMessage(error.message + ': ' + error.response.data.Coupon);
            });
    }


    return (
        <div className="container">
         <h1 className="bordered-text" >just order it</h1>

            
            {message && <div className="text-class  mx-4 " >{message.split('\n').map((item, key) => {
                    return <span key={key}>{item}<br/></span>
                })}</div>}
  
            <div>
                <table className="table">
                    <thead>
                            <tr>
                                <th>Vendor</th>
                                <th>Product</th>
                                <th>Price</th>
                                <th>Order</th>
                                <th>getCoupon</th>
                                <th>Update</th>
                                <th>Delete</th>    
                                <th>OrderError</th> 
                            </tr>
                    </thead>
                    <tbody>
                    {
                        products.map( product =>  {                       
                               const hasCoupon = coupons[product.vendor] && coupons[product.vendor] !== '_NONE_';
                               const noCoupon=hasCoupon && coupons[product.vendor] === 'no coupon'
                               const coupon= coupons[product.vendor]|| '_NONE_';
                               const rating= ratings[product.vendor]|| 0;
                               product.coupon=coupon;
                               product.rating=rating;
                               return (     
                                <tr key={product.id}> 
                                    <td>{product.vendor} &nbsp;
                                    {hasCoupon && !noCoupon &&(
                                        <span className="btn btn-info btn-sm" title={coupon}></span>
                                    )}
                                    {noCoupon && (
                                        <span className="btn btn-warning btn-sm" title={coupon}></span>
                                    )}
                                    {!hasCoupon && (
                                        <span className="btn btn-secondary btn-sm " title="click getGoupon"></span>
                                )}
                                    </td>
                                    <td>{product.name}</td>
                                    <td>{product.price.toString()}</td>
                                    <td> <button className="btn btn-primary" 
                                                    onClick={() => orderproduct(product,1)}>Order</button> </td>
                                    <td> <button className="btn btn-info" 
                                                    onClick={() => getCouponRating(product)}>getCoupon</button> </td>                                                                                 
                                    <td> <button className="btn btn-success" 
                                                    onClick={() => updateproduct(product.id)}>Update</button> </td>                                    
                                    <td> <button className="btn btn-secondary" 
                                                    onClick={() => deleteproduct(product.id)}>Delete</button> </td>
                                    <td> <button className="btn btn-warning" 
                                                    onClick={() => orderproduct(product,410)}>Order410</button> 
                                                    &nbsp;
                                        <button className="btn btn-danger" 
                                                    onClick={() => orderproduct(product,501)}>Order501</button>                                                     
                                                    </td>
                                                    
                                </tr>
                            )}
                        )
                    }
                    </tbody>

                </table>
            </div>
            <div className="btn btn-success m-5" onClick={addnewproduct}>Add New product</div>
        </div>
    )
}

export default ListProductsComponent