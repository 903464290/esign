window.onload = function (){
  document.getElementById('download')
  .addEventListener("click",()=>{
    const content = this.document.getElementById('content');
    console.log(content);
    console.log(window);
    var element = document.getElementById('element-to-print');
    var opt = {  
      margin:   1,
      filename:     'myfile.pdf',
      image:        { type: 'jpeg', quality: 0.98 },
      html2canvas:  { scale: 2},
      jsPDF:        { unit: 'mm', format: 'a4', orientation: 'portrait' }
    };
 
    html2pdf().from(content).set(opt).save();
  })
}