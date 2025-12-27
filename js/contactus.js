var testimonialSwiper = new Swiper(".testimonial-swiper", {
  loop: true,
  autoplay: {
    delay: 3500,
    disableOnInteraction: false,
  },
  speed: 700,
  spaceBetween: 30,
  grabCursor: true,

  slidesPerView: 1,   // Always ONE card

  pagination: {
    el: ".testimonial-pagination",
    clickable: true,
  }
});


const reveals = document.querySelectorAll(".reveal");

function revealOnScroll() {
  const windowHeight = window.innerHeight;

  reveals.forEach(el => {
    const elementTop = el.getBoundingClientRect().top;
    const elementVisible = 100;

    if (elementTop < windowHeight - elementVisible) {
      el.classList.add("active");
    }
  });
}

window.addEventListener("scroll", revealOnScroll);
window.addEventListener("load", revealOnScroll);