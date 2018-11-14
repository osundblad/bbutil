**pitest**

`mvn org.pitest:pitest-maven:mutationCoverage`
`mvn -DwithHistory org.pitest:pitest-maven:mutationCoverage`

**Release to oss.sonatype.org**

`mvn clean install deploy -P release`

[se.eris@oss.sonatype.org](https://oss.sonatype.org/#nexus-search;quick~se.eris)
