package com.example.monomi.core.database.entity.mapper

import com.example.monomi.core.database.entity.BaseEntity
import com.example.monomi.core.model.BaseModel

interface EntityMapper<Domain: BaseModel, Entity: BaseEntity> {

  fun asEntity(domain: Domain): Entity

  fun asDomain(entity: Entity): Domain
}