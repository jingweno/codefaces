<?php global $titan; ?>
</div><!--end wrapper-->
</div><!--end content-background-->
<div id="footer">
	<div class="wrapper clear">
<!--
		<div id="footer-about" class="footer-column">
			<h2><?php _e('About', 'titan'); ?></h2>
			<?php if ($titan->footerAbout() != '') : ?>
				<?php echo $titan->footerAbout(); ?>
			<?php else : ?>
				<p><?php _e("Did you know you can write your own about section just like this one? It's really easy. Head into the the <em>Titan Options</em> menu and check out the footer section. Type some stuff in the box, click save, and your new about section shows up in the footer.", "titan"); ?></p>
				<p><?php _e("Wondering about those Flickr photos on the right?", "titan"); ?></p>
				<p><?php _e("We didn't take them, they are a random sampling of the most popular photos on Flickr with the tag 'nature'. All rights are reserved to the original copyright holders where applicable.", "titan"); ?></p>
			<?php endif; ?>
		</div>
		<div id="footer-search" class="footer-column">
			<h2><?php _e('Search', 'titan'); ?></h2>
			<?php if (is_file(STYLESHEETPATH . '/searchform.php')) include (STYLESHEETPATH . '/searchform.php'); else include(TEMPLATEPATH . '/searchform.php'); ?>
		</div>
-->
		<div id="copyright">
			<p class="copyright-notice"><?php _e('Copyright', 'titan'); ?> &copy; <?php echo date('Y'); ?> <?php echo $titan->copyrightName(); ?>.</p>
		</div><!--end copyright-->
	</div><!--end wrapper-->
</div><!--end footer-->
<?php wp_footer(); ?>
<?php
	if ($titan->statsCode() != '') {
		echo $titan->statsCode();
	}
?>
</body>
</html>

