getquand()
    const apple ="https://www.quandl.com/api/v3/datasets/EOD/AAPL?api_key=qw3kpYTbygFpCm-WSMdf"

    async function getquand(){
    const response = await fetch(apple)
    const data1 = await response.json()

    document.getElementById("livedata").textContent=data1.dataset.data[0][1]
    console.log(data1.dataset.data[0][1])
}
    getquand()


    const HD ="https://www.quandl.com/api/v3/datasets/EOD/HD?api_key=qw3kpYTbygFpCm-WSMdf"

    async function getquand1(){
    const response = await fetch(HD)
    const data1 = await response.json()

    document.getElementById("livedata2").textContent=data1.dataset.data[0][1]
    console.log(data1.dataset.data[0][1])
}
    getquand1()

    const JPM ="https://www.quandl.com/api/v3/datasets/EOD/JPM?api_key=qw3kpYTbygFpCm-WSMdf"

    async function getquand2(){
    const response = await fetch(JPM)
    const data1 = await response.json()

    document.getElementById("livedata3").textContent=data1.dataset.data[0][1]
    console.log(data1.dataset.data[0][1])
}
    getquand2()

    const crypt_com_url="https://min-api.cryptocompare.com/data/pricemulti?fsyms=BTC,ETH,BSV&tsyms=USD,GBP$api_key="
    const my_api_key="4e8c6724e6416bc345a32984b8e557cf93a3348f963c1c5b3059023734f844f5"

    const my_crypto_url=crypt_com_url+my_api_key

    async function getcrypt(){
        const response = await fetch(my_crypto_url);
        const data = await response.json();
    
        document.getElementById("livedata4").textContent=data.BTC.USD;
       
    }
    
    getcrypt()
    async function getcrypt1(){
        const response = await fetch(my_crypto_url);
        const data = await response.json();
    
        document.getElementById("livedata5").textContent=data.ETH.USD;
       
    }
    getcrypt1()

    async function getcrypt2(){
        const response = await fetch(my_crypto_url);
        const data = await response.json();
    
        document.getElementById("livedata6").textContent=data.BSV.USD;
       
    }
    getcrypt2()

   

