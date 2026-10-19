<h1 align="center">🧵 Tailor - Full-Stack Bespoke E-Commerce & Custom Tailoring Platform</h1>

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-orange.svg?style=for-the-badge&logo=openjdk" alt="Java">
  <img src="https://img.shields.io/badge/Spring_Boot-3.2-brightgreen.svg?style=for-the-badge&logo=springboot" alt="Spring Boot">
  <img src="https://img.shields.io/badge/Database-H2%20%2F%20MySQL-blue.svg?style=for-the-badge&logo=hibernate" alt="Database">
  <img src="https://img.shields.io/badge/Cloud-Cloudinary%20CDN-blueviolet.svg?style=for-the-badge&logo=cloudinary" alt="Cloudinary">
  <img src="https://img.shields.io/badge/Cloud-AWS%20S3-FF9900.svg?style=for-the-badge&logo=amazonaws&logoColor=white" alt="AWS S3">
  <img src="https://img.shields.io/badge/Frontend-HTML5%20%2F%20CSS3%20%2F%20JS-informational.svg?style=for-the-badge&logo=javascript" alt="Frontend">
</p>

<p align="center">
  <b>A full-stack Java Spring Boot web platform bridging traditional bespoke tailoring with modern digital retail.</b>
</p>

<hr>

<h2>📌 Project Overview</h2>
<p>
  <b>Tailor</b> is a modern full-stack web application designed for custom tailoring studios and fashion retailers. 
  Developed as part of our 2nd Year 1st Semester Object-Oriented Programming (OOP) with Java module, it offers a dual shopping experience:
</p>
<ul>
  <li><b>Readymade Retail & Raw Materials Store:</b> Customers can browse off-the-rack fashion collections, purchase raw fabrics per meter, select garment sizes, and manage persistent shopping carts.</li>
  <li><b>Bespoke Custom Tailoring Engine:</b> Customers can order custom-made suits, blazers, shirts, and trousers by inputting precise measurements (chest, waist, inseam, sleeve, and length).</li>
</ul>

<hr>

<h2>📸 Platform Visual Showcase</h2>

<table width="100%" border="0" cellspacing="0" cellpadding="10">
  <tr>
    <td width="50%" align="center" valign="top">
      <div style="border: 1px solid #e1e4e8; border-radius: 12px; padding: 12px; background: #ffffff;">
        <img src="https://raw.githubusercontent.com/2nd-Year-Projects-uni/Java-/develop/frontend/images/first4.png" alt="Digital Storefront Showcase" width="100%" style="border-radius: 8px;">
        <br><br>
        <h3>🌐 Digital Storefront & Service Portal</h3>
        <p><b>Brand Experience:</b> <i>Home Landing Page, About Us Story, Custom Tailoring Services, and Contact Us</i></p>
      </div>
    </td>
    <td width="50%" align="center" valign="top">
      <div style="border: 1px solid #e1e4e8; border-radius: 12px; padding: 12px; background: #ffffff;">
        <img src="https://raw.githubusercontent.com/2nd-Year-Projects-uni/Java-/develop/frontend/images/last4.png" alt="Retail & Order Tracking Showcase" width="100%" style="border-radius: 8px;">
        <br><br>
        <h3>🛒 Retail Shopping, Checkout & Tracking</h3>
        <p><b>E-Commerce Workflow:</b> <i>Interactive Shop Catalog, Shopping Cart, Real-Time Order Tracking, and User Authentication</i></p>
      </div>
    </td>
  </tr>
</table>

<hr>

<h2>✨ Key Features</h2>

<table width="100%">
  <thead>
    <tr>
      <th>Category</th>
      <th>Feature</th>
      <th>Description</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>📏 <b>Bespoke Tailoring</b></td>
      <td><b>Custom Measurement Engine</b></td>
      <td>Enter custom chest, waist, sleeve, length, and inseam measurements for tailored apparel.</td>
    </tr>
    <tr>
      <td>🛍️ <b>E-Commerce Shop</b></td>
      <td><b>Dynamic Product Catalog</b></td>
      <td>Browse readymade garments and raw fabrics per meter with responsive category filters.</td>
    </tr>
    <tr>
      <td>🛒 <b>Cart & Checkout</b></td>
      <td><b>Persistent Shopping Cart</b></td>
      <td>Real-time tax (2%) & delivery fee calculations with promotional free-shipping logic over Rs 20,000.</td>
    </tr>
    <tr>
      <td>🚚 <b>Order Tracking</b></td>
      <td><b>Visual Progress Pipeline</b></td>
      <td>Real-time multi-stage order tracking (<i>Confirmed → Tailoring → Quality Check → Delivered</i>).</td>
    </tr>
    <tr>
      <td>⚙️ <b>Admin Dashboard</b></td>
      <td><b>Inventory Control & Reports</b></td>
      <td>Full CRUD product operations, live drag-and-drop image upload preview, and automated CSV sales export.</td>
    </tr>
    <tr>
      <td>☁️ <b>Cloud Infrastructure</b></td>
      <td><b>Hybrid Media & Storage Engine</b></td>
      <td>Embedded H2 & MySQL database support, paired with Cloudinary CDN and AWS S3 media management.</td>
    </tr>
  </tbody>
</table>

<hr>

<h2>💻 Technologies Used</h2>

<table>
  <tr>
    <td><b>Backend Core</b></td>
    <td>Java 21, Spring Boot 3.2 (Spring MVC, Spring Data JPA, Hibernate)</td>
  </tr>
  <tr>
    <td><b>Database Systems</b></td>
    <td>Embedded H2 File Database (<code>jdbc:h2:file:./data/tailorshopdb</code>) & MySQL Support</td>
  </tr>
  <tr>
    <td><b>Cloud & Media Storage</b></td>
    <td>Cloudinary Java SDK, Cloudinary Global CDN, AWS S3 & Local Storage Fallback</td>
  </tr>
  <tr>
    <td><b>Frontend UI</b></td>
    <td>HTML5, CSS3, JavaScript (ES6+), Bootstrap 4.4.1, FontAwesome 6.5</td>
  </tr>
  <tr>
    <td><b>APIs & Protocols</b></td>
    <td>RESTful Web APIs, JSON Serialization, Multipart File Uploads</td>
  </tr>
  <tr>
    <td><b>Build & Tools</b></td>
    <td>Apache Maven, Git / GitHub, Visual Studio Code</td>
  </tr>
</table>

<hr>

<h2>🎨 UI/UX Design Case Study</h2>
<p>
  Check out the complete visual UI design showcase and case study on <b>Behance</b>:<br>
  👉 <a href="https://www.behance.net/gallery/242378331/Tailor-Website-UIUX-Design" target="_blank"><b>View Behance UI/UX Showcase</b></a>
</p>

<hr>

<h2>🚀 Getting Started</h2>

<h3>1. Clone the Repository</h3>
<pre><code>git clone https://github.com/2nd-Year-Projects-uni/Java-.git
cd Java-</code></pre>

<h3>2. Configure Properties</h3>
<pre><code>cp src/main/resources/application.properties.example src/main/resources/application.properties</code></pre>

<h3>3. Run the Backend</h3>
<pre><code>.\mvnw.cmd spring-boot:run</code></pre>

<hr>

<h2>💡 P.S. Architectural Evolution & Design Decisions</h2>

<p>
  <i>During the development lifecycle of this project, we evolved our architecture to optimize performance, cloud efficiency, and developer experience:</i>
</p>

<ul>
  <li>
    <b>🗄️ Database Evolution (MySQL → Embedded H2 Database):</b><br>
    We initially built the platform using an external <b>MySQL Server</b>. To make the project <b>zero-setup and portable out of the box</b> for evaluators and teammates, we migrated to an embedded file-based <b>H2 Database</b> (<code>jdbc:h2:file:./data/tailorshopdb</code>). This eliminates the need for anyone cloning the repository to install, configure, or run a local MySQL service, while maintaining 100% full SQL, Hibernate, and JPA entity capabilities.
  </li>
  <br>
  <li>
    <b>☁️ Cloud Media Evolution (AWS S3 → Cloudinary CDN):</b><br>
    We originally integrated <b>AWS S3</b> for remote asset storage. However, we transitioned to <b>Cloudinary CDN</b> to leverage its automatic real-time image compression, dynamic format optimization (WebP/AVIF), and ultra-fast global CDN delivery. Furthermore, we engineered an automatic <b>local storage fallback mechanism</b> so product image uploads continue to work seamlessly even if cloud APIs are offline.
  </li>
</ul>


