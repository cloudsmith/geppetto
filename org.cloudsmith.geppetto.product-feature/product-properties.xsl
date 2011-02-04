<?xml version="1.0" encoding="UTF-8"?>
<xsl:transform version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" />

	<xsl:template match="/">
		<product>
			<xsl:for-each select="/product/@name">
				<name>
					<xsl:value-of select="." />
				</name>
			</xsl:for-each>

			<xsl:for-each select="(/product/@uid | /product/@id)[position() = 1]">
				<iu>
					<xsl:value-of select="." />
				</iu>
			</xsl:for-each>

			<xsl:for-each select="/product/launcher[position() = 1]/@name">
				<launcher.name>
					<xsl:value-of select="." />
				</launcher.name>
			</xsl:for-each>
		</product>
	</xsl:template>
</xsl:transform>
