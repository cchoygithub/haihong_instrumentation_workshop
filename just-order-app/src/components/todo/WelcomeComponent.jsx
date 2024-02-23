import {useParams, Link} from 'react-router-dom'
import {useState} from 'react'
import {orderproductApi} from './api/ProductApiService'


function WelcomeComponent() {

    const {username} = useParams()

    // const authContext = useAuth()


    const [message, setMessage] = useState("hello there...")

    function callOrderRestApi(vendor, product, quantity) {
        console.log('called')
        setMessage("ordering your phone..." + vendor + ":" + product + ":" + quantity)
        const coupon = "_NONE_"
        const rating = 0
        orderproductApi({vendor, product, quantity, coupon, rating})
            .then((response) => successfulResponse(response))
            .catch((error) => errorResponse(error))
            .finally(() => console.log('cleanup'))
    }

    function successfulResponse(response) {
        console.log(response)
        //setMessage(response.data)
        setMessage(response.data)
    }

    function errorResponse(error) {
        console.log(error)
        setMessage(error.message)
    }


    return (
        <div className="WelcomeComponent">

            <h1 className="bordered-text">welcome <span className="btn btn-dark btn-lg shadow-sm">{username}</span></h1>

            <h5  >
                explore other options - <Link to="/products" className='link-success shadow-sm '> go here </Link>
            </h5>
         
            <div >
                <button className="btn btn-success btn-lg m-5 " onClick={() => callOrderRestApi('apple', 'iphone', 1)}>
                    just order apple
                </button>
                {/* <button className="btn btn-success btn-lg m-5" onClick={() => callOrderRestApi('apple','iphone',410)}>
                order apple error</button>      */}

                <button className="btn btn-primary btn-lg m-5" onClick={() => callOrderRestApi('samsung', 'z fold', 1)}>
                    just order samsung
                </button>
                {/* <button className="btn btn-primary m-5" onClick={() => callOrderRestApi('samsung','z fold',410)}>
                order samsung error</button> */}

                <button className="btn btn-danger btn-lg m-5"
                        onClick={() => callOrderRestApi('huawei', 'mateX3', 1)}>
                    just order huawei
                </button>
                {/* <button className="btn btn-danger text-white m-5" onClick={() => callOrderRestApi('huawei','mateX3',410)}>
                order huawei Error</button>                                        */}

            </div>

            <div className="text-class">{message.split('\n').map((item, key) => {
                return <span key={key}>{item}<br/></span>
            })}</div>
        </div>
    )
}

export default WelcomeComponent