<?xml version="1.0" encoding="UTF-8"?>
<xsl:transform version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="text" />

	<xsl:template match="/">
		<!-- output the id of the feature -->
		<xsl:value-of select="/feature/@id" />
	</xsl:template>
</xsl:transform>
