# Credentials

## Principle

Credentials are **defined** at the *Template* level and contain the following information:

* Label (mandatory)
* Default username (optional)
* Default password (optional)

*Hosts* using a *Template* must specify a username and password for each of the Credentials defined in this *Template*. Otherwise, the default username and password defined in the *Template* will be used.

<div class="alert alert-info"><i class="icon-hand-up"></i><strong>Note: </strong>It is not recommended to set default usernames and passwords in the <i>Templates</i> since these properties are supposed to be specific to <i>Hosts.</i></div>

The *Monitors* may specify the use of any of the Credentials defined in the *Template* to which they belong. The usernames and passwords, as configured in the *Host* for the corresponding Credentials, will be used at run time.

In addition to the Credentials created at the *Template* level, *Monitors* may use the *System Credentials* defined for each *Host*. If the *System Credentials* are left empty, the PATROL Agent default account is used.

## How it works

The internal mechanism of the KM for handling credentials works as below:

1. The *Host* discovery sets the list of available credentials to all its hostname instances including the credentials set in the *Host* configuration. These credentials are not used directly by *Monitors*. However, they are used for *Host* availability checks. When using CMA, these credentials come from the policy.
2. The *Template* discovery reads these credentials and builds the list of appropriate credentials to be used by the monitors attached to the *Template*. These credentials are a combination of the *Host* and the default *Template* credentials. They are saved in the *Template* namespace.
3. The *Monitor* discoveries inherits the credentials from the *Template*. If a *Monitor* requires credentials that have not been properly configured, the value of the *Template*'s **Collection Error Count** parameter is increased by one.
