window.addEventListener("load",()=> {
    const canavs=document.querySelector("#canvas");
    const ctx =canvas.getContext("2d");

//variables
   let painting =false;

   function startPosition(){
       painting =true;
   }
   function finishedPosition(){
       painting= false;
       ctx.beginPath();
   }

   function draw(e){
       if(!painting) return ;
       ctx.lineWidth=10;
       ctx.lineCap="round";

       ctx.lineTo(e.clietX, e.clientY);
       ctx.stroke();
       ctx.beginPath();
       ctx.moveTo(e.clientX, e.clientY);
   }

//EventListeners





})


