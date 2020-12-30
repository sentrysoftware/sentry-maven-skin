// Verify that the files have been created properly
assert new File(basedir, "target/site/index.html").isFile()
assert new File(basedir, "target/site/subdir/agent.html").isFile()

// Verify that the created HTML files contain the proper information
String result = new File(basedir, "target/site/events.html").text

assert result.contains("<b>skin-test</b> allows you")
assert result.contains("<title>Managing Events &ndash; skin-test Extended</title>")
assert result =~ '<li><a +href="https://youtu.be/Th6NweyurWs">YouTube</a></li>'
assert result.contains('<img src="./images/Events.png" alt="Events Page" zoomable-img="" class=" zoomable-img-loading" />')
assert result.contains('<table border="0" class="bodyTable table table-striped table-hover">')
assert result.contains('<h2 id="Filtering_Events">Filtering Events</h2>')
assert result.contains('<h2 id="Keyboard_Shortcuts_28special29">Keyboard Shortcuts (special)</h2>')
assert result.contains('<li><a href="#Keyboard_Shortcuts_28special29" du-smooth-scroll="">Keyboard Shortcuts (special)</a></li>')
assert result =~ /(?s)toc-inline-container.*Table of Contents.*ul id="toc"/
assert result.indexOf('<ul id="toc">') != result.lastIndexOf('<ul id="toc">')
assert result.contains('<meta name="keywords" content="event,testevent,blank space,studio,km,patrol,develop,web" />')
assert result.contains("Documentation as of <strong>1975-03-24 19:30:00</strong>")
assert result =~ /<meta name="generator" content="Maven Site Plugin, Doxia Site Renderer .*, Skin sentry-maven-skin .*, from markdown"/
assert result.contains('<body class="sentry-site sentry-studio"')
assert result =~ /(?s)header-title.*skin-test Extended.*header-subtitle.*Version <strong>1.0-SNAPSHOT-test/
assert result.contains('<h5 class="text-uppercase">Getting Started</h5>')
assert result.contains('<li><a  href="console.html">Operating the Console</a></li>')
assert result.contains('<li class="active"><a  href="events.html">Managing Events</a></li>')
assert result.contains('<li><a  href="subdir/agent.html">Configuring the Agent</a></li>')
assert result =~ /(?s)<div class="toc">.*<li><a href="#Filtering_Events" du-smooth-scroll="">Filtering Events/
assert result =~ /Keywords:.*<span class="label.*">testevent<.*<span class="label.*">blank space<.*<span class="label.*">web</
assert result =~ /(?s)<div class="footer">.*skin-test Extended 1.0-SNAPSHOT-test/
assert result =~ /Documentation as of.*1975-03-24 19:30:00*/
assert result =~ /Copyright.*20[1-9][0-9]/

// Verify that index.json contains the proper information
indexJsonFile = new File(basedir, "target/site/index.json")
assert indexJsonFile.isFile()
String indexJson = indexJsonFile.text
assert indexJson.contains('"events.html":{"id":"events.html","title":"Managing Events","keywords":"event,testevent,blank space,studio,km,patrol,develop,web"')
