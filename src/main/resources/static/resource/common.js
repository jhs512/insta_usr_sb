function isTouchDevice() {
  return (('ontouchstart' in window) ||
          (navigator.maxTouchPoints > 0) ||
          (navigator.msMaxTouchPoints > 0));
}

const $html = document.querySelector('html');
$html.classList.add(isTouchDevice() ? 'touch-posible' : 'touch-imposible');