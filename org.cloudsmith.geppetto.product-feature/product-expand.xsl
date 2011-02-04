<?xml version="1.0" encoding="UTF-8"?>
<xsl:transform version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" />

	<xsl:param name="feature-manifest" />

	<!-- extract the version from the feature manifest -->
	<xsl:variable name="product-version" select="document($feature-manifest)/feature/@version" />

	<!-- override the product version -->
	<xsl:template match="/product">
		<xsl:copy>
			<xsl:apply-templates select="@*" />
			<xsl:attribute name="version"><xsl:value-of select="$product-version" /></xsl:attribute>
			<xsl:apply-templates select="node()" />
		</xsl:copy>
	</xsl:template>

	<!-- identity transform idiom -->
	<xsl:template match="node() | @*">
		<xsl:copy>
			<xsl:apply-templates select="node() | @*" />
		</xsl:copy>
	</xsl:template>
</xsl:transform>
