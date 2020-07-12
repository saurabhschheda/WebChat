const containerClassList = document.getElementsByClassName('container')[0].classList;
let isActive = false;

function toggleActiveClass() {
  if (isActive) {
    containerClassList.remove('active');
    isActive = false;
  }
  else {
    isActive = true;
    containerClassList.add('active');
  }
}
