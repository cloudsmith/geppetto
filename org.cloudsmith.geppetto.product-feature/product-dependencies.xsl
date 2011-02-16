<?xml version="1.0" encoding="UTF-8"?>
<xsl:transform version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="text" />

	<xsl:param name="skip-count" select="0" />
	<xsl:param name="line-speparator" select="'&#xa;'" />

	<xsl:variable name="feature-element" select="'includes'" />
	<xsl:variable name="bundle-element" select="'plugin'" />

	<xsl:variable name="feature-suffix" select="'.feature.group'" />
	<xsl:variable name="feature-suffix-length" select="string-length($feature-suffix)" />

	<xsl:template match="/">
		<xsl:for-each select="/feature/*[name() = $feature-element or name() = $bundle-element]">
			<xsl:variable name="prefix" select="concat('requires.', position() + $skip-count, '.')" />
			<xsl:variable name="suffix"
				select="substring($feature-suffix, 1, number(name() = $feature-element) * $feature-suffix-length)" />
			<xsl:variable name="range">
				<xsl:call-template name="create-compatible-version-range">
					<xsl:with-param name="version" select="@version" />
				</xsl:call-template>
			</xsl:variable>

			<xsl:value-of select="concat($prefix, 'namespace', '=', 'org.eclipse.equinox.p2.iu', $line-speparator)" />
			<xsl:value-of select="concat($prefix, 'name', '=', @id, $suffix, $line-speparator)" />
			<xsl:value-of select="concat($prefix, 'range', '=', $range, $line-speparator)" />
		</xsl:for-each>
	</xsl:template>

	<!-- create a compatible dependency version range -->
	<xsl:template name="create-compatible-version-range">
		<xsl:param name="version" />

		<xsl:variable name="version-string" select="string($version)" />

		<xsl:variable name="major-version" select="substring-before($version-string, '.')" />
		<xsl:variable name="minor-string" select="substring-after($version-string, '.')" />
		<xsl:variable name="minor-version" select="substring-before($minor-string, '.')" />

		<xsl:value-of
			select="concat('[', $major-version, '.', $minor-version, '.', '0', ',', $major-version + 1, '.', '0', '.', '0', ')')" />
	</xsl:template>
</xsl:transform>
