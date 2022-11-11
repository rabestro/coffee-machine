import lv.id.jc.machine.tag.UnitTest

report {
    issueNamePrefix 'Issue #'
    issueUrlPrefix 'https://github.com/rabestro/coffee-machine/issues/'
}

spockReports {
    set 'com.athaydes.spockframework.report.showCodeBlocks': false
    set 'com.athaydes.spockframework.report.outputDir': 'build/reports/tests'
    set 'com.athaydes.spockframework.report.projectName': 'Coffee Machine'
    set 'com.athaydes.spockframework.report.projectVersion': 1.0
}

runner {
    def unit = System.getenv("UNIT")

    if (unit == 'FALSE') {
        exclude.annotations << UnitTest
    }

    if (unit == 'TRUE') {
        include.annotations << UnitTest
    }
}