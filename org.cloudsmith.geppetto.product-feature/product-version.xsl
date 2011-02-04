<?xml version="1.0" encoding="UTF-8"?>
<xsl:transform version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="text" />

	<xsl:param name="product-iu" />

	<xsl:template match="/">
		<!-- output the version of the first matching installable unit -->
		<xsl:value-of select="(/profile/units/unit[@id = $product-iu])/@version" />
	</xsl:template>
</xsl:transform>
