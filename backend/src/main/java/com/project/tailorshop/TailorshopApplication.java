package com.project.tailorshop;

import com.project.tailorshop.entities.AdminUser;
import com.project.tailorshop.entities.CustomProduct;
import com.project.tailorshop.entities.Product;
import com.project.tailorshop.repositories.AdminUserRepository;
import com.project.tailorshop.repositories.CustomProductRepository;
import com.project.tailorshop.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TailorshopApplication {

    public static void main(String[] args) {
        SpringApplication.run(TailorshopApplication.class, args);
    }

    @Bean
    CommandLineRunner initData(ProductRepository productRepository,
                               CustomProductRepository customProductRepository,
                               AdminUserRepository adminUserRepository) {
        return args -> {
            // Seed Default Admin Account if none exists
            if (adminUserRepository.count() == 0) {
                adminUserRepository.save(new AdminUser("admin@tailorshop.com", "admin123"));
                System.out.println(">>> Initialized default admin: admin@tailorshop.com / admin123");
            }

            // Seed Ready-made Products and Materials ONLY if database is empty
            if (productRepository.count() == 0) {
                // --- New Collection (from Home Page) ---
                productRepository.save(new Product(
                        "Satin Oversize Blouse",
                        "Luxurious satin oversized blouse with a smooth silky finish and elegant drape.",
                        4450.00,
                        "Women",
                        "images/front1.png"
                ));

                productRepository.save(new Product(
                        "Pleated A-Line Skirt",
                        "Classic pleated A-line skirt tailored for modern elegance.",
                        4950.00,
                        "Women",
                        "images/front2.png"
                ));

                productRepository.save(new Product(
                        "Linen Puff-Sleeve Crop Top",
                        "Breathable natural linen crop top featuring feminine puff sleeves.",
                        3950.00,
                        "Women",
                        "images/front3.png"
                ));

                productRepository.save(new Product(
                        "Bespoke Silk Wrap Dress",
                        "Flattering mulberry silk wrap dress with adjustable waist tie and v-neckline.",
                        9800.00,
                        "Women",
                        "images/Bespoke Silk Wrap Dress.jfif"
                ));

                productRepository.save(new Product(
                        "Cropped Denim Jacket",
                        "Stylish cropped denim jacket with premium brass button detailing.",
                        6500.00,
                        "Women",
                        "images/front8.png"
                ));

                productRepository.save(new Product(
                        "Evening Gown",
                        "Sophisticated floor-length evening dress crafted for special occasions.",
                        15500.00,
                        "Women",
                        "images/front5.png"
                ));

                productRepository.save(new Product(
                        "Summer Floral Dress",
                        "Lightweight summer dress featuring vibrant floral patterns and comfortable fit.",
                        5400.00,
                        "Women",
                        "images/front6.png"
                ));

                productRepository.save(new Product(
                        "Cashmere-Blend Ribbed Knit Cardigan",
                        "Soft button-front layering cardigan made from a cozy cashmere-wool blend.",
                        11200.00,
                        "Women",
                        "images/Cashmere-Blend Ribbed Knit Cardigan.jfif"
                ));

                // --- Men's Collection ---
                productRepository.save(new Product(
                        "Relaxed Fit Oxford Chambray Shirt",
                        "Soft washed 100% cotton chambray button-down shirt for effortless daily comfort.",
                        5400.00,
                        "Men",
                        "images/Relaxed Fit Oxford Chambray Shirt.jfif"
                ));

                productRepository.save(new Product(
                        "Pique Cotton Tailored Polo",
                        "Breathable pima cotton polo with ribbed collar and tailored sleeves.",
                        4200.00,
                        "Men",
                        "images/Pique Cotton Tailored Polo.jfif"
                ));

                productRepository.save(new Product(
                        "Corduroy Utility Overshirt",
                        "Soft textured fine-wale corduroy shirt jacket with dual chest pockets.",
                        7800.00,
                        "Men",
                        "images/Corduroy Utility Overshirt.jfif"
                ));

                productRepository.save(new Product(
                        "Mandarin Collar Cuban Linen Shirt",
                        "Airy pure linen band-collar casual shirt designed for warm relaxed days.",
                        5900.00,
                        "Men",
                        "images/Mandarin Collar Cuban Linen Shirt.jfif"
                ));

                productRepository.save(new Product(
                        "Casual Linen Summer Shirt",
                        "Bespoke short-sleeve or long-sleeve linen shirt for effortless smart casual style.",
                        5200.00,
                        "Men",
                        "images/Casual Linen Summer Shirt.jfif"
                ));

                productRepository.save(new Product(
                        "Essential Cotton Crewneck T-Shirt",
                        "Super soft 100% combed cotton classic crewneck t-shirt built for everyday comfort.",
                        3800.00,
                        "Men",
                        "images/Essential Cotton Crewneck T-Shirt.jfif"
                ));

                productRepository.save(new Product(
                        "Crewneck Fine Knit Sweater",
                        "Ultra-soft fine merino knitwear for refined layering in cool weather.",
                        12500.00,
                        "Men",
                        "images/Crewneck Fine Knit Sweater.jfif"
                ));

                productRepository.save(new Product(
                        "Premium Tailored Pima Cotton T-Shirt",
                        "Heavyweight 100% pima cotton custom tailored t-shirt with reinforced crew neck.",
                        3500.00,
                        "Men",
                        "images/Premium Tailored Pima Cotton T-Shirt.jfif"
                ));

                // --- Materials ---
                productRepository.save(new Product(
                        "Pure Cashmere Wool Fabric",
                        "Ultra-soft 100% cashmere wool fabric imported from Italy.",
                        4500.00,
                        "Materials",
                        "images/Pure Cashmere Wool Fabric.jfif"
                ));

                productRepository.save(new Product(
                        "Premium Raw Silk Fabric",
                        "Natural raw silk fabric with a subtle sheen, ideal for custom tailoring.",
                        3800.00,
                        "Materials",
                        "images/Premium Raw Silk Fabric.jfif"
                ));

                productRepository.save(new Product(
                        "Egyptian Long-Staple Cotton Fabric",
                        "Crisp, highly durable long-staple 100% Egyptian cotton fabric for fine shirts.",
                        2900.00,
                        "Materials",
                        "images/Egyptian Long-Staple Cotton Fabric.jfif"
                ));

                productRepository.save(new Product(
                        "Pure Organic Irish Linen Fabric",
                        "Lightweight, airy natural flax linen fabric perfect for summer suits and casual wear.",
                        3400.00,
                        "Materials",
                        "images/Pure Organic Irish Linen Fabric.jfif"
                ));

                productRepository.save(new Product(
                        "Fine Tweed Houndstooth Wool",
                        "Classic structured suiting wool with traditional houndstooth pattern.",
                        4200.00,
                        "Materials",
                        "images/Fine Tweed Houndstooth Wool.jfif"
                ));

                productRepository.save(new Product(
                        "Japanese Selvedge Denim (Raw / Indigo)",
                        "Premium raw Japanese denim with deep indigo dye and signature selvedge ID.",
                        4800.00,
                        "Materials",
                        "images/Japanese Selvedge Denim.jfif"
                ));

                productRepository.save(new Product(
                        "Fine Ribbed Merino Corduroy",
                        "Luxurious merino wool blend corduroy fabric with velvety ribbed texture.",
                        3950.00,
                        "Materials",
                        "images/Fine Ribbed Merino Corduroy.jfif"
                ));

                productRepository.save(new Product(
                        "Mercerized Cotton Sateen",
                        "Silky-smooth mercerized cotton sateen fabric with brilliant luster and softness.",
                        3200.00,
                        "Materials",
                        "images/Mercerized Cotton Sateen.jfif"
                ));

                System.out.println(">>> Initialized sample products and materials");
            }

            // Seed Craft Your Own Custom Products ONLY if database is empty
            if (customProductRepository.count() == 0) {

            customProductRepository.save(new CustomProduct(
                    "Classic Tailored Blazer",
                    "Bespoke single-breasted wool blazer custom tailored to your exact measurements.",
                    24500.00,
                    "Blazer",
                    "images/Classic Tailored Blazer.jfif"
            ));

            customProductRepository.save(new CustomProduct(
                    "Tailored Men's Jacket",
                    "Structured formal suit jacket with peak lapels and premium inner lining.",
                    18900.00,
                    "Jacket",
                    "images/Tailored Men's Jacket.jfif"
            ));

            customProductRepository.save(new CustomProduct(
                    "Egyptian Cotton Shirt",
                    "Breathable luxury Egyptian cotton shirt custom tailored for daily sophistication.",
                    6500.00,
                    "Shirt",
                    "images/Egyptian Cotton Shirt.jfif"
            ));

            customProductRepository.save(new CustomProduct(
                    "Italian Linen Summer Suit",
                    "Airy lightweight Italian linen 2-piece summer suit for warm-weather elegance.",
                    28500.00,
                    "Shirt",
                    "images/Italian Linen Summer Suit.jfif"
            ));

            customProductRepository.save(new CustomProduct(
                    "Bespoke Charcoal Tuxedo",
                    "Classic charcoal dinner tuxedo with satin lapel trimming for black-tie occasions.",
                    35000.00,
                    "Tuxedo",
                    "images/Bespoke Charcoal Tuxedo.jfif"
            ));

            customProductRepository.save(new CustomProduct(
                    "Custom Fit White Oxford Shirt",
                    "Crisp 100% cotton Oxford dress shirt crafted to fit your silhouette.",
                    5800.00,
                    "Shirt",
                    "images/Custom Fit White Oxford Shirt.jfif"
            ));

            customProductRepository.save(new CustomProduct(
                    "Tailored Wool Trousers",
                    "Sharp flat-front wool trousers with custom waist and inseam adjustment.",
                    9200.00,
                    "Trousers",
                    "images/Tailored Wool Trousers.jfif"
            ));

            customProductRepository.save(new CustomProduct(
                    "Classic Double-Breasted Waistcoat",
                    "Sophisticated 6-button double-breasted suit vest with adjustable back buckle strap.",
                    7800.00,
                    "Waistcoat",
                    "images/Classic Double-Breasted Waistcoat.jfif"
            ));

            customProductRepository.save(new CustomProduct(
                    "Tailored Women’s Executive Business Suit",
                    "Sharp single-breasted executive business suit jacket crafted for modern professional women.",
                    14800.00,
                    "Blazer",
                    "images/front4.png"
            ));

            customProductRepository.save(new CustomProduct(
                    "Tailored Women's High-Waist Business Trousers",
                    "Elegant high-waisted wide-leg business trousers tailored for office wear.",
                    8900.00,
                    "Trousers",
                    "images/Tailored Women's High-Waist Business Trousers.jfif"
            ));

            customProductRepository.save(new CustomProduct(
                    "Double-Breasted Women's Power Suit Set",
                    "Bespoke double-breasted structured power blazer matched with slim-fit trousers.",
                    18500.00,
                    "Blazer",
                    "images/Double-Breasted Women's Power Suit Set.jfif"
            ));

            customProductRepository.save(new CustomProduct(
                    "Double-Breasted Tailored Blazer Dress",
                    "Modern double-breasted blazer dress featuring double button closure and sharp lapels.",
                    16500.00,
                    "Dress",
                    "images/Double-Breasted Tailored Blazer Dress.jfif"
            ));


            System.out.println(">>> Initialized custom products");
            }
        };
    }

}

