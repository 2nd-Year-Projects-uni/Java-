document.addEventListener("DOMContentLoaded", function () {

       //HERO TYPING EFFECT
	
    const words = ["Tailored", "Measured", "Crafted", "Styled"];
    const typedSpan = document.getElementById("typedWord");

    let wordIndex = 0;
    let charIndex = 0;
    let isDeleting = false;

    function typeEffect() {
        const currentWord = words[wordIndex];

        if (!isDeleting) {
            typedSpan.textContent = currentWord.substring(0, charIndex + 1);
            charIndex++;

            if (charIndex === currentWord.length) {
                setTimeout(() => (isDeleting = true), 1000);
            }
        } else {
            typedSpan.textContent = currentWord.substring(0, charIndex - 1);
            charIndex--;

            if (charIndex === 0) {
                isDeleting = false;
                wordIndex = (wordIndex + 1) % words.length;
            }
        }

        setTimeout(typeEffect, isDeleting ? 200 : 150);
    }
    typeEffect();

       //REVEAL ON SCROLL

    const items = document.querySelectorAll(".reveal-item");

    const observer = new IntersectionObserver(entries => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                let delay = entry.target.getAttribute("data-delay") || "0s";
                entry.target.style.animationDelay = delay;
                entry.target.classList.add("show");
            }
        });
    }, { threshold: 0.2 });

    items.forEach(el => observer.observe(el));

       //INDIVIDUAL IMAGE TRACKING

    document.querySelectorAll(".sig-row").forEach(row => {

        const img = row.querySelector(".sig-moving-img");
        const word = row.querySelector(".sig-word");

        let currentX = 0;
        let targetX = 0;
        const ease = 0.12;   

        function animate() {
            const diff = targetX - currentX;

            if (Math.abs(diff) > 0.5) {
                currentX += diff * ease;
                img.style.left = `${currentX}px`;
                requestAnimationFrame(animate);
            } else {
                currentX = targetX;
                img.style.left = `${currentX}px`;
            }
        }
  
        row.addEventListener("mouseenter", () => {
            img.classList.add("show");
        });
        
        row.addEventListener("mouseleave", () => {
            img.classList.remove("show");
        });
   
        row.addEventListener("mousemove", e => {

            const rowRect = row.getBoundingClientRect();
            const wordRect = word.getBoundingClientRect();
       
            const mouseX = e.clientX - rowRect.left;
       
            const minX = wordRect.left - rowRect.left;
            const maxX = wordRect.right - rowRect.left;

         
            targetX = Math.max(minX, Math.min(mouseX, maxX));

            requestAnimationFrame(animate);
        });

    });

});




