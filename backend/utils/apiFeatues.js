

class ApiFeatures 
{
    constructor(query,queryString)
    {
        this.query = query;
        this.queryString = queryString;

    }
    search()
    {
        const keyword = this.queryString.keyword ? {
                name:{
                    $regex:this.queryString.keyword,
                    $options:'i'
                }
        }:{}
        // console.log(keyword);
        this.query = this.query.find({...keyword})
        return this;
    }
    filter()
    {
        const queryCopy  = {...this.queryString};
        // console.log(queryCopy);
        
        const removeFields = ['keyword','limit','page']
        removeFields.forEach(element=> delete queryCopy[element]);
        // console.log(queryCopy);

        let queryString = JSON.stringify(queryCopy)
        
        queryString = queryString.replace(/\b(gt|gte|lt|lte)\b/g, match=> "$"+match);


        this.query = this.query.find(JSON.parse(queryString));
        return this;
    }
    pagination(responsePerPage)
    {
        const currentPage = Number(this.queryString.page || 1);
        const skip = responsePerPage * (currentPage-1);
        this.query  = this.query.limit(responsePerPage).skip(skip);
        return this;
    }
};
module.exports = ApiFeatures;