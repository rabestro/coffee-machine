package lv.id.jc.machine.exception

import lv.id.jc.machine.model.Resource

class NotEnoughResourcesException(val missingResources: Set<Resource>) :
    IllegalArgumentException("not enough resources")