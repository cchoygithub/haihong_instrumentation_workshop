import { apiClient } from './ApiClient'

//products
    
export const retrieveAllproductsApi
= () => apiClient.get(`/products`)

export const deleteproductApi
    = (id) => apiClient.delete(`/products/${id}`)

export const retrieveproductApi
    = (id) => apiClient.get(`/products/${id}`)

export const updateproductApi
    = (id, product) => apiClient.put(`/products/${id}`, product)

export const createproductApi
    = ( product) => apiClient.post(`/products`, product)

export const orderproductApi
    = ( product) => apiClient.post(`/restapi/order?coupon=${product.coupon}`, product)

export const vendordetailsApi
= ( product) => apiClient.get(`/restapi/vendor-details/${product.vendor}`)

// health status
export const HealthApi
    = "/actuator/health"
